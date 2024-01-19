/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.feature.repository.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.core.data.UserDataRepository
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.feature.repository.search.RepositoryInfoFragmentArgs
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

    @Inject lateinit var userDataRepository: UserDataRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CodeCheckAppTheme {
                    RepositoryInfoRoute(
                        repositorySummary = args.githubRepositorySummary
                    )
                }
            }
        }
    }

    /**
     * FragmentのViewが生成された後に呼び出される。
     *
     * 最後に検索した日時をログで表示している。現状はそれ以外は特にしていない
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
    }
}
