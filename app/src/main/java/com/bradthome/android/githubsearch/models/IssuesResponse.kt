package com.bradthome.android.githubsearch.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by bradley.thome on 10/18/17.
 */

@JsonClass(generateAdapter = true)
class IssuesResponse : Results<IssueItem>() {

    @Json(name = "items")
    override val items: List<IssueItem>? = null

}

@JsonClass(generateAdapter = true)
class IssueItem : ResultsItem() {

    @Json(name = "url")
    val url: String? = null

    @Json(name = "repository_url")
    val repositoryUrl: String? = null

    @Json(name = "labels_url")
    val labelsUrl: String? = null

    @Json(name = "comments_url")
    val commentsUrl: String? = null

    @Json(name = "events_url")
    val eventsUrl: String? = null

    @Json(name = "id")
    val id: Int = 0

    @Json(name = "number")
    val number: Int = 0

    @Json(name = "title")
    val title: String? = null

    @Json(name = "user")
    val user: User? = null

    @Json(name = "labels")
    val labels: List<Label>? = null

    @Json(name = "state")
    val state: String? = null

    @Json(name = "assignee")
    val assignee: Any? = null

    @Json(name = "milestone")
    val milestone: Any? = null

    @Json(name = "comments")
    val comments: Int = 0

    @Json(name = "created_at")
    val createdAt: String? = null

    @Json(name = "updated_at")
    val updatedAt: String? = null

    @Json(name = "closed_at")
    val closedAt: Any? = null

    @Json(name = "pull_request")
    val pullRequest: PullRequest? = null

    @Json(name = "body")
    val body: String? = null

    @Json(name = "score")
    val score: Float = 0.toFloat()

}

class Label {

    @Json(name = "url")
    val url: String? = null

    @Json(name = "name")
    val name: String? = null

    @Json(name = "color")
    val color: String? = null

}

@JsonClass(generateAdapter = true)
class PullRequest {

    @Json(name = "html_url")
    val htmlUrl: Any? = null

    @Json(name = "diff_url")
    val diffUrl: Any? = null

    @Json(name = "patch_url")
    val patchUrl: Any? = null

}

@JsonClass(generateAdapter = true)
class User {

    @Json(name = "login")
    val login: String? = null

    @Json(name = "id")
    val id: Int = 0

    @Json(name = "avatar_url")
    val avatarUrl: String? = null

    @Json(name = "gravatar_id")
    val gravatarId: String? = null

    @Json(name = "url")
    val url: String? = null

    @Json(name = "html_url")
    val htmlUrl: String? = null

    @Json(name = "followers_url")
    val followersUrl: String? = null

    @Json(name = "following_url")
    val followingUrl: String? = null

    @Json(name = "gists_url")
    val gistsUrl: String? = null

    @Json(name = "starred_url")
    val starredUrl: String? = null

    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String? = null

    @Json(name = "organizations_url")
    val organizationsUrl: String? = null

    @Json(name = "repos_url")
    val reposUrl: String? = null

    @Json(name = "events_url")
    val eventsUrl: String? = null

    @Json(name = "received_events_url")
    val receivedEventsUrl: String? = null

    @Json(name = "type")
    val type: String? = null

}
