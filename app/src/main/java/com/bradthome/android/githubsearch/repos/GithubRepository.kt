package com.bradthome.android.githubsearch.repos

import com.bradthome.android.githubsearch.core.GitResult
import com.bradthome.android.githubsearch.models.SearchOptions
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

    private suspend fun <T : Any> getCache(searchOptions: SearchOptions<T>): T? {
        return mutex.withLock {
            cache[searchOptions]?.let { it as T }
        }
    }

    private suspend fun <T : Any> saveCache(searchOptions: SearchOptions<T>, value: T) {
        return mutex.withLock {
            cache[searchOptions] = value
        }
    }

    suspend fun <T : Any> fetch(searchOptions: SearchOptions<T>): GitResult<T> {
        return withContext(context = coroutineContext) {
            searchOptions.run {
                GitResult.tryCatchSuspend {
                    getCache(searchOptions = searchOptions) ?: api.networkCall().also {
                        saveCache(searchOptions, it)
                    }
                }
            }
        }
    }

}