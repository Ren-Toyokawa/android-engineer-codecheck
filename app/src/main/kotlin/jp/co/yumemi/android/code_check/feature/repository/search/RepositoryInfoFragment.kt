/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.feature.repository.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.core.data.UserDataRepository
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryInfoBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * リポジトリ情報画面
 */
@AndroidEntryPoint
class RepositoryInfoFragment : Fragment(R.layout.fragment_repository_info) {
    companion object {
        private const val TAG = "RepositoryInfoFragment"
    }

    private val args: RepositoryInfoFragmentArgs by navArgs()

    private var binding: FragmentRepositoryInfoBinding? = null

    @Inject lateinit var userDataRepository: UserDataRepository

    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBindingのインスタンスを破棄する
        // FragmentのインスタンスはonDestroyView以降も破棄されない
        // そのため、ここでViewBindingのインスタンスを破棄する必要がある
        binding = null
    }

    /**
     * FragmentのViewが生成された後に呼び出される。
     * ここで、リポジトリ情報をViewにバインドし、UIの更新をしている。
     *
     * @param view このFragmentのRoot View
     * @param savedInstanceState このフラグメントの以前の保存状態を含むBundle、再作成時にのみ非nullとなる
     */
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val lastSearchDate = userDataRepository.latestSearchDate.first()
            Log.d(TAG, "検索した日時: $lastSearchDate")
        }

        binding = FragmentRepositoryInfoBinding.bind(view)

        applyRepositorySummary()
    }

    private fun applyRepositorySummary () {
        val repositorySummary = args.githubRepositorySummary

        binding?.apply {
            ownerIconView.load(repositorySummary.ownerIconUrl) {
                placeholder(R.drawable.ic_android)
                error(R.drawable.ic_android)
            }
            nameView.text = repositorySummary.name
            languageView.text = if (repositorySummary.language !== null){
                getString(R.string.written_language, repositorySummary.language)
            } else {
                getString(R.string.language_not_specified)
            }
            starsView.text = getString(R.string.star_count_text, repositorySummary.stargazersCount)
            watchersView.text = getString(R.string.watcher_count_text, repositorySummary.watchersCount)
            forksView.text = getString(R.string.fork_count_text, repositorySummary.forksCount)
            openIssuesView.text = getString(R.string.open_issue_count_text, repositorySummary.openIssuesCount)
        }
    }
}
