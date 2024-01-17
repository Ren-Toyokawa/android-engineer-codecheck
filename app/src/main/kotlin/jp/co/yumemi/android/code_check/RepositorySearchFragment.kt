/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.core.data.model.GithubRepository
import jp.co.yumemi.android.code_check.databinding.FragmentRepositorySearchBinding
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
                    viewModel.executeSearchRepository(editText.text.toString())
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
     * @param githubRepository ユーザーによって選択されたリポジトリの情報
     */
    private fun navigateRepositoryInfoFragment(githubRepository: GithubRepository) {
        val action =
            RepositorySearchFragmentDirections
                .actionRepositorySearchFragmentToRepositoryInfoFragment(
                    githubRepository = githubRepository,
                )

        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }
}
