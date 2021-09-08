package com.bradthome.android.githubsearch.network


import com.bradthome.android.githubsearch.models.Commits
import com.bradthome.android.githubsearch.models.Issues
import com.bradthome.android.githubsearch.models.Repositories
import com.bradthome.android.githubsearch.models.Users
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by bradley.thome on 10/17/17.
 */
private const val ACCEPT = "Accept: application/vnd.github.cloak-preview+json"

interface GithubApi {

    @GET("search/repositories")
    @Headers(ACCEPT)
    suspend fun repos(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
    ): Repositories

    @GET("search/issues")
    @Headers(ACCEPT)
    fun issues(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    )
            : Issues

    @GET("search/users")
    @Headers(ACCEPT)
    fun users(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    )
            : Users

    @GET("search/commits")
    @Headers(ACCEPT)
    fun commits(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    )
            : Commits
}

