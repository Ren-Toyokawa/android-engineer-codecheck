package jp.co.yumemi.android.code_check.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepository
import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindGithubRepositoryRepository(
        githubRepositoryRepositoryImpl: GithubRepositoryRepositoryImpl
    ): GithubRepositoryRepository
}