package com.bradthome.android.githubsearch.core.hilt.modules

import com.bradthome.android.githubsearch.repos.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGithubRepository(okHttpClient: OkHttpClient): GithubRepository {
        return GithubRepository(okHttpClient)
    }

}