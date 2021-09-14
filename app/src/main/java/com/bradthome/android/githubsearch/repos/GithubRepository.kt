package com.bradthome.android.githubsearch.repos

import com.bradthome.android.githubsearch.core.GitResult
import com.bradthome.android.githubsearch.models.Results
import com.bradthome.android.githubsearch.models.ResultsItem
import com.bradthome.android.githubsearch.models.SearchOptions
import com.bradthome.android.githubsearch.models.SearchResults
import com.bradthome.android.githubsearch.network.GithubApi
import com.squareup.moshi.Moshi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import kotlin.coroutines.CoroutineContext

class GithubRepository(
    val api: GithubApi,
    val moshi: Moshi,
    val coroutineContext: CoroutineContext,
    val okHttpClient: OkHttpClient,
) {

    private val cache = mutableMapOf<SearchOptions<*>, Any>()
    private val mutex = Mutex()

    private suspend fun <T : ResultsItem> getCache(searchOptions: SearchOptions<T>): Results<T>? {
        return mutex.withLock {
            cache[searchOptions]?.let { it as Results<T> }
        }
    }

    private suspend fun <T : ResultsItem> saveCache(searchOptions: SearchOptions<T>, value: Results<T>) {
        return mutex.withLock {
            cache[searchOptions] = value
        }
    }

    suspend fun <T : ResultsItem> fetch(searchOptions: SearchOptions<T>): SearchResults<T> {
        return withContext(context = coroutineContext) {
            searchOptions.run {
                SearchResults(searchOptions = searchOptions, state = GitResult.tryCatchSuspend {
                    getCache(searchOptions = searchOptions) ?: networkCall(api).also {
                        saveCache(searchOptions, it)
                    }
                })

            }
        }
    }

}