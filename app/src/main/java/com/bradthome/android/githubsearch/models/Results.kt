package com.bradthome.android.githubsearch.models

import com.bradthome.android.githubsearch.core.GitResult
import com.squareup.moshi.Json
import java.lang.Math.ceil

/**
 * Created by bradley.thome on 10/17/17.
 */

interface Results<resultsItemType : ResultsItem> {
    @Json(name = "total_count")
    val totalCount: Int?

    @Json(name = "incomplete_results")
    val incompleteResults: Boolean?

    @Json(name = "items")
    val items: List<resultsItemType>?

    val totalPages
        get() = when (val count = totalCount) {
            null -> null
            0 -> 0
            else -> ceil(30.0 / count.toDouble()).toInt()
        }
}


interface ResultsItem {
    @Json(name = "html_url")
    val htmlUrl: String?
}

data class SearchResults<T : ResultsItem>(val searchOptions: SearchOptions<T>, val state: GitResult<out Results<T>>)

