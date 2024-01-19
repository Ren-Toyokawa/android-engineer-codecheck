package jp.co.yumemi.android.code_check.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import jp.co.yumemi.android.code_check.core.data.GithubIssueRepository
import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepository
import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepositoryImpl
import jp.co.yumemi.android.code_check.core.data.OfflineUserDataRepository
import jp.co.yumemi.android.code_check.core.data.UserDataRepository
import jp.co.yumemi.android.code_check.core.data.di.RepositoryModule
import jp.co.yumemi.android.code_check.core.data.fake.FakeGithubIssueRepository
import jp.co.yumemi.android.code_check.core.data.fake.FakeGithubRepositoryRepository
import jp.co.yumemi.android.code_check.core.data.fake.FakeUserDataRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
interface TestRepositoryModule {
    @Binds
    fun bindGithubRepositoryRepository(
        githubRepositoryRepositoryImpl: FakeGithubRepositoryRepository
    ): GithubRepositoryRepository

    @Binds
    fun bindUserDataRepository(
        userDataRepository: FakeUserDataRepository
    ): UserDataRepository

    @Binds
    fun bindGithubIssueRepository(
        githubIssueRepository: FakeGithubIssueRepository
    ): GithubIssueRepository
}