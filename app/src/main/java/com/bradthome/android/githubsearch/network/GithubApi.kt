package com.bradthome.android.githubsearch.network


import com.bradthome.android.githubsearch.models.CommitsResponse
import com.bradthome.android.githubsearch.models.IssuesResponse
import com.bradthome.android.githubsearch.models.RepositoriesResponse
import com.bradthome.android.githubsearch.models.UsersResponse
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
    ): RepositoriesResponse

    @GET("search/issues")
    @Headers(ACCEPT)
    suspend fun issues(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    )
            : IssuesResponse

    @GET("search/users")
    @Headers(ACCEPT)
    suspend fun users(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    )
            : UsersResponse

    @GET("search/commits")
    @Headers(ACCEPT)
    suspend fun commits(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    )
            : CommitsResponse

    companion object {
        const val GITHUB_HOST = "https://api.github.com"
    }
}

