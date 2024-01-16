/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryInfoBinding

/**
 * リポジトリ情報画面
 */
class RepositoryInfoFragment : Fragment(R.layout.fragment_repository_info) {
    companion object {
        private const val TAG = "RepositoryInfoFragment"
    }

    private val args: RepositoryInfoFragmentArgs by navArgs()

    private var binding: FragmentRepositoryInfoBinding? = null

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

        Log.d(TAG, "検索した日時: $lastSearchDate")

        binding = FragmentRepositoryInfoBinding.bind(view)

        val repositoryInfo = args.repositoryInfoItem

        binding?.apply {
            ownerIconView.load(repositoryInfo.owner.avatarUrl) {
                placeholder(R.drawable.ic_android)
                error(R.drawable.ic_android)
            }
            nameView.text = repositoryInfo.name
            languageView.text = if (repositoryInfo.language !== null){
                getString(R.string.written_language, repositoryInfo.language)
            } else {
                getString(R.string.language_not_specified)
            }
            starsView.text = getString(R.string.star_count_text, repositoryInfo.stargazersCount)
            watchersView.text = getString(R.string.watcher_count_text, repositoryInfo.watchersCount)
            forksView.text = getString(R.string.fork_count_text, repositoryInfo.forksCount)
            openIssuesView.text = getString(R.string.open_issue_count_text, repositoryInfo.openIssuesCount)
        }
    }
}
