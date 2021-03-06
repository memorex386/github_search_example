package com.bradthome.android.githubsearch.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserItem(

    @Json(name = "login")
    val login: String? = null,

    @Json(name = "id")
    val id: Int = 0,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "gravatar_id")
    val gravatarId: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "followers_url")
    val followersUrl: String? = null,

    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String? = null,

    @Json(name = "organizations_url")
    val organizationsUrl: String? = null,

    @Json(name = "repos_url")
    val reposUrl: String? = null,

    @Json(name = "received_events_url")
    val receivedEventsUrl: String? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "score")
    val score: Float = 0.toFloat(),

    @Json(name = "html_url")
    override val htmlUrl: String? = null,

    ) : ResultsItem

@JsonClass(generateAdapter = true)
data class UsersResponse(

    @Json(name = "total_count")
    override val totalCount: Int? = null,

    @Json(name = "incomplete_results")
    override val incompleteResults: Boolean? = null,

    @Json(name = "items")
    override val items: List<UserItem>? = null,

    ) : Results<UserItem>
