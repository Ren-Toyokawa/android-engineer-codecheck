/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.FragmentRepositorySearchBinding

/**
 * リポジトリ検索画面
 * 画面上部のテキストフィールドに入力された文字列を元に、Githubのレポジトリを検索する
 */
class RepositorySearchFragment : Fragment(R.layout.fragment_repository_search) {
    /**
     * Viewが生成された後に呼び出される
     *
     * ここで、以下の処理を行っている
     * - View Binding
     * - ViewModelの初期化
     * - RecyclerView のセットアップ
     * - 検索ボタンタップ時の挙動の設定
     *
     * @param view View
     * @param savedInstanceState 保存されたインスタンスの状態
     */
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepositorySearchBinding.bind(view)

        val viewModel = RepositorySearchViewModel(context!!)

        val layoutManager = LinearLayoutManager(context!!)

        val dividerItemDecoration =
            DividerItemDecoration(context!!, layoutManager.orientation)

        val adapter =
            RepositoryInfoAdapter(
                object : RepositoryInfoAdapter.OnItemClickListener {
                    override fun itemClick(repositoryInfoItem: RepositoryInfoItem) {
                        navigateRepositoryInfoFragment(repositoryInfoItem)
                    }
                },
            )

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        // IMEの検索ボタンが押されたときに、Githubのレポジトリを検索
                        // 結果をRecyclerViewに表示する
                        viewModel.searchRepository(it).apply {
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    /**
     * リポジトリ情報画面に遷移する
     * @param repositoryInfoItem ユーザーによって選択されたリポジトリの情報
     */
    fun navigateRepositoryInfoFragment(repositoryInfoItem: RepositoryInfoItem) {
        val action =
            RepositorySearchFragmentDirections
                .actionRepositorySearchFragmentToRepositoryInfoFragment(
                    repositoryInfoItem = repositoryInfoItem,
                )
        findNavController().navigate(action)
    }
}


// RecyclerViewのアイテムの差分を計算し、 必要なアップデートのみを行うようにするためのCallback
val diffUtil =
    object : DiffUtil.ItemCallback<RepositoryInfoItem>() {
        /**
         * 名前を比較し、二つのアイテムが同一のアイテムを表しているかどうかを判断する
         * @param oldItem 古いリポジトリ情報
         * @param newItem 新しいリポジトリ情報
         */
        override fun areItemsTheSame(
            oldItem: RepositoryInfoItem,
            newItem: RepositoryInfoItem,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        /**
         * 二つのアイテムのデータ内容が等しいかどうかを判断する
         * @param oldItem 古いリポジトリ情報
         * @param newItem 新しいリポジトリ情報
         */
        override fun areContentsTheSame(
            oldItem: RepositoryInfoItem,
            newItem: RepositoryInfoItem,
        ): Boolean {
            return oldItem == newItem
        }
    }

/**
 * リポジトリ情報をRecyclerViewに表示するためのアダプター
 */
class RepositoryInfoAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<RepositoryInfoItem, RepositoryInfoAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(repositoryInfoItem: RepositoryInfoItem)
    }

    /**
     * ViewHolderが生成された際に呼び出される
     *
     * @param parent 親のView
     * @param viewType Viewの種別, ここでは1つしかないので未使用
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * ViewHolderにデータをバインドする
     *
     * @param holder ViewHolder
     * @param position リストの位置
     */
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
