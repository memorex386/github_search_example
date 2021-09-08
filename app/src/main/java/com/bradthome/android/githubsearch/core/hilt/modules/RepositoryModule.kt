package com.bradthome.android.githubsearch.core.hilt.modules

import com.bradthome.android.githubsearch.network.GithubApi
import com.bradthome.android.githubsearch.repos.GithubRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGithubRepository(
        @GithubOkHttpClient okHttpClient: OkHttpClient,
        api: GithubApi,
        moshi: Moshi,
    ): GithubRepository {
        return GithubRepository(
            okHttpClient = okHttpClient,
            api = api,
            moshi = moshi,
            coroutineContext = Dispatchers.IO
        )
    }

}