package com.bradthome.android.githubsearch.models


import android.os.Parcelable
import com.bradthome.android.githubsearch.network.GithubApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.parcelize.Parcelize


/**
 * Created by bradley.thome on 10/17/17.
 */

enum class Order(val order: String, val title: String) {
    ASC("asc", "Ascending"),
    DESC("desc", "Descending")
}

enum class SortOptions(val key: String, val value: String) {
    STARS("stars", "Stars"),
    FORKS("forks", "Forks"),
    UPDATED("updated", "Updated"),
    CREATED("created", "Created"),
    AUTHOR_DATE("author-date", "Author Date"),
    COMMITTER_DATE("committer-date", "Committer Date"),
    COMMENTS("comments", "Comments"),
    FOLLOWERS("followers", "Followers"),
    REPOS("repositories", "Repositories"),
    JOINED("joined", "Joined"),
    BEST_MATCH("", "Best Match");

    class SortOptionsAdapter {
        @ToJson
        fun toJson(type: SortOptions): String {
            return type.key
        }

        @FromJson
        fun fromJson(value: String): SortOptions = SortOptions.values().firstOrNull { it.key == value } ?: BEST_MATCH
    }
}

/**
 * The different github api options in enum form, including a SearchViewContainer constructor for
 * each one (using a 'when' statement which means that each one is required to have an
 * associated searchViewContainer)
 */


sealed class SearchApis<T : ResultsItem>(
    val title: String,
    val networkCall: suspend GithubApi.(query: String, pageNumber: Int, sort: String?, order: String?) -> Results<T>,
    val sortOptions: Set<SortOptions>,
) : Parcelable {
    companion object {
        val values by lazy {
            SearchApis::class.sealedSubclasses.map { it.objectInstance as SearchApis }
        }
    }
}

@Parcelize
object SearchRepositories : SearchApis<GithubRepo>("Repos",
    GithubApi::repos,
    setOf(SortOptions.STARS, SortOptions.FORKS, SortOptions.UPDATED, SortOptions.BEST_MATCH))

@Parcelize
object SearchCommits : SearchApis<CommitItem>("Commits",
    GithubApi::commits,
    setOf(SortOptions.AUTHOR_DATE, SortOptions.COMMITTER_DATE, SortOptions.BEST_MATCH))

@Parcelize
object SearchIssues : SearchApis<IssueItem>("Issues",
    GithubApi::issues,
    setOf(SortOptions.COMMENTS, SortOptions.CREATED, SortOptions.UPDATED, SortOptions.BEST_MATCH))

@Parcelize
object SearchUsers : SearchApis<UserItem>("Users",
    GithubApi::users,
    setOf(SortOptions.FOLLOWERS, SortOptions.REPOS, SortOptions.JOINED, SortOptions.BEST_MATCH))


/**
 * Container to store all the api variables
 */
@Parcelize
data class SearchOptions<T : ResultsItem>(
    val search: SearchApis<T>,
    val searchQuery: SearchQuery,
) : Parcelable {
    suspend fun networkCall(githubApi: GithubApi): Results<T> {
        return search.networkCall(githubApi,
            searchQuery.query.text,
            searchQuery.page,
            searchQuery.sort.key,
            searchQuery.order.order)
    }
}

@JvmInline
value class Query(val text: String) {
    init {
        require(text.isNotBlank()) {
            "Query Param cannot be blank"
        }
    }
}

@Parcelize
data class SearchQuery(
    val query: Query,
    val page: Int = 1,
    val sort: SortOptions = SortOptions.BEST_MATCH,
    val order: Order = Order.DESC,
) : Parcelable


