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
 * FIXME: コメントが適切でないため、コメント修正ブランチで修正する
 */
class RepositoryInfoFragment : Fragment(R.layout.fragment_repository_info) {
    companion object {
        private const val TAG = "RepositoryInfoFragment"
    }

    private val args: RepositoryInfoFragmentArgs by navArgs()

    private var binding: FragmentRepositoryInfoBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "検索した日時: $lastSearchDate")

        binding = FragmentRepositoryInfoBinding.bind(view)

        val repositoryInfo = args.repositoryInfo

        _binding.ownerIconView.load(repositoryInfo.ownerIconUrl);
        _binding.nameView.text = repositoryInfo.name;
        _binding.languageView.text = repositoryInfo.language;
        _binding.starsView.text = "${repositoryInfo.stargazersCount} stars";
        _binding.watchersView.text = "${repositoryInfo.watchersCount} watchers";
        _binding.forksView.text = "${repositoryInfo.forksCount} forks";
        _binding.openIssuesView.text = "${repositoryInfo.openIssuesCount} open issues";
    }
}
