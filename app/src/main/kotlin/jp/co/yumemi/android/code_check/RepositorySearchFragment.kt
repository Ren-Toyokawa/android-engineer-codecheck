/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.FragmentRepositorySearchBinding
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * リポジトリ検索画面
 * 画面上部のテキストフィールドに入力された文字列を元に、Githubのレポジトリを検索する
 */
class RepositorySearchFragment : Fragment(R.layout.fragment_repository_search) {
    private var binding: FragmentRepositorySearchBinding? = null

    private val viewModel: RepositorySearchViewModel by viewModels()

    /**
     * Viewが生成された後に呼び出される
     *
     * ここで、以下の処理を行っている
     * - View Binding
     * - ViewModelの初期化
     * - RecyclerView のセットアップ
     * - 検索ボタンタップ時の挙動の設定
     *
     * @param view
     * @param savedInstanceState 保存されたインスタンスの状態
     */
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRepositorySearchBinding.bind(view)

        setupRecyclerView()
        setupSearchInput()
        applySearchResult()
        errorHandling()
    }

    /**
     * RecyclerViewのセットアップを行う
     */
    private fun setupRecyclerView() {
        val context = requireContext()
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            layoutManager.orientation
        )
        val adapter = RepositoryInfoAdapter { repositoryInfoItem ->
            navigateRepositoryInfoFragment(repositoryInfoItem)
        }

        binding?.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.addItemDecoration(dividerItemDecoration)
            recyclerView.adapter = adapter
        }
    }

    /**
     * 検索ボタンがタップされた際の挙動を設定する
     */
    private fun setupSearchInput() {
        binding?.apply {
            searchInputText.setOnEditorActionListener { editText, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchRepository(editText.text.toString())
                    true
                } else {
                    false
                }
            }
        }
    }

    /**
     * 検索結果をRecyclerViewに反映する
     */
    private fun applySearchResult() {
        viewModel
            .searchResults
            .onEach { searchResults ->
                val adapter = binding?.recyclerView?.adapter as? RepositoryInfoAdapter
                // HACK: 厳密なハンドリングをするメリットが少ないためここではnullの場合はreturnする
                adapter ?: return@onEach

                adapter.submitList(searchResults)
            }
            .launchIn(lifecycleScope)
    }

    /**
     * エラーの状態を監視し、エラーが発生した場合にダイアログを表示する
     */
    private fun errorHandling() {
        val context = requireContext()
        viewModel
            .errorState
            .onEach {
                when (it) {
                    ErrorState.CantFetchRepositoryInfo -> showCantFetchRepositoryInfoDialog(context)
                    ErrorState.NetworkError -> showNetworkErrorDialog(context)
                    ErrorState.Idle -> { /* 何もしない */ }
                }
            }
            .launchIn(lifecycleScope)
    }

    /**
     * ネットワークエラーのダイアログを表示する
     */
    private fun showNetworkErrorDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.network_error_dialog_title)
            .setMessage(R.string.network_error_dialog_message)
            .setPositiveButton(R.string.network_error_dialog_positive_button) { _, _ ->
                viewModel.clearErrorState()
            }
            .show()
    }

    /**
     * リポジトリ情報の取得に失敗した場合のダイアログを表示する
     */
    private fun showCantFetchRepositoryInfoDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.error_dialog_title)
            .setMessage(R.string.error_dialog_message)
            .setPositiveButton(R.string.error_dialog_positive_button) { _, _ ->
                viewModel.clearErrorState()
            }
            .show()
    }

    /**
     * リポジトリ情報画面に遷移する
     * @param repositoryInfoItem ユーザーによって選択されたリポジトリの情報
     */
    private fun navigateRepositoryInfoFragment(repositoryInfoItem: RepositoryInfoItem) {
        val action =
            RepositorySearchFragmentDirections
                .actionRepositorySearchFragmentToRepositoryInfoFragment(
                    repositoryInfoItem = repositoryInfoItem,
                )

        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }
}

/**
 * リポジトリ情報をRecyclerViewに表示するためのアダプター
 */
class RepositoryInfoAdapter(
    private val itemClickListener: (RepositoryInfoItem) -> Unit,
) : ListAdapter<RepositoryInfoItem, RepositoryInfoAdapter.ViewHolder>(DiffCallback) {
    // region inner class, objectの定義
    class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * RecyclerViewのアイテムの差分を計算し、 必要なアップデートのみを行うようにするためのCallback
     */
    object DiffCallback : DiffUtil.ItemCallback<RepositoryInfoItem>() {
        /**
         * 名前を比較し、二つのアイテムが同一のアイテムを表しているかどうかを判断する
         *
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
         *
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

    // endregion

    // region override methods

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
        val binding =
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ViewHolder(binding)
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
        holder.binding.repositoryNameView.text = item.name
        holder.itemView.setOnClickListener {
            itemClickListener(item)
        }
    }
    // endregion
}
