package com.bradthome.android.githubsearch.repos

import com.bradthome.android.githubsearch.models.SearchOptions
import com.bradthome.android.githubsearch.network.GithubApi
import com.squareup.moshi.Moshi
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import kotlin.coroutines.CoroutineContext

class GithubRepository(
    val api: GithubApi,
    val moshi: Moshi,
    val coroutineContext: CoroutineContext,
    val okHttpClient: OkHttpClient,
) {

    suspend inline fun <reified T> fetch(searchOptions: SearchOptions<T>): T {
        return withContext(context = coroutineContext) {
            searchOptions.run {
                api.networkCall()
            }
        }
    }

}