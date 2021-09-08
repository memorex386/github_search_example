package com.bradthome.android.githubsearch.models

import com.squareup.moshi.Json

/**
 * Created by bradley.thome on 10/17/17.
 */

abstract class Results<resultsItemType : ResultsItem>() {
    @Json(name = "total_count")
    val totalCount: Int? = null

    @Json(name = "incomplete_results")
    val incompleteResults: Boolean? = null

    @Json(name = "items")
    open val items: List<resultsItemType>? = null
}


abstract class ResultsItem() {
    @Json(name = "html_url")
    val htmlUrl: String? = null
}

