package com.bradthome.android.githubsearch.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by bradley.thome on 10/18/17.
 */


@JsonClass(generateAdapter = true)
data class CommitsResponse(
    @Json(name = "items")
    override val items: List<CommitItem>? = null,
) : Results<CommitItem>()


@JsonClass(generateAdapter = true)
data class GithubAuthor(

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

    @Json(name = "html_url")
    val htmlUrl: String? = null,

    @Json(name = "followers_url")
    val followersUrl: String? = null,

    @Json(name = "following_url")
    val followingUrl: String? = null,

    @Json(name = "gists_url")
    val gistsUrl: String? = null,

    @Json(name = "starred_url")
    val starredUrl: String? = null,

    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String? = null,

    @Json(name = "organizations_url")
    val organizationsUrl: String? = null,

    @Json(name = "repos_url")
    val reposUrl: String? = null,

    @Json(name = "events_url")
    val eventsUrl: String? = null,

    @Json(name = "received_events_url")
    val receivedEventsUrl: String? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "site_admin")
    val siteAdmin: Boolean = false,

    )

@JsonClass(generateAdapter = true)
data class CommitAuthor(

    @Json(name = "date")
    val date: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "email")
    val email: String? = null,

    )

@JsonClass(generateAdapter = true)
data class Commit(

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "author")
    val author: CommitAuthor? = null,

    @Json(name = "committer")
    val committer: Committer? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "tree")
    val tree: Tree? = null,

    @Json(name = "comment_count")
    val commentCount: Int = 0,

    )

@JsonClass(generateAdapter = true)
data class RepoCommitter(

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

    @Json(name = "html_url")
    val htmlUrl: String? = null,

    @Json(name = "followers_url")
    val followersUrl: String? = null,

    @Json(name = "following_url")
    val followingUrl: String? = null,

    @Json(name = "gists_url")
    val gistsUrl: String? = null,

    @Json(name = "starred_url")
    val starredUrl: String? = null,

    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String? = null,

    @Json(name = "organizations_url")
    val organizationsUrl: String? = null,

    @Json(name = "repos_url")
    val reposUrl: String? = null,

    @Json(name = "events_url")
    val eventsUrl: String? = null,

    @Json(name = "received_events_url")
    val receivedEventsUrl: String? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "site_admin")
    val siteAdmin: Boolean = false,

    )

@JsonClass(generateAdapter = true)
data class Committer(

    @Json(name = "date")
    val date: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "email")
    val email: String? = null,

    )

@JsonClass(generateAdapter = true)
data class CommitItem(

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "sha")
    val sha: String? = null,

    @Json(name = "comments_url")
    val commentsUrl: String? = null,

    @Json(name = "commit")
    val commit: Commit? = null,

    @Json(name = "author")
    val author: GithubAuthor? = null,

    @Json(name = "committer")
    val committer: RepoCommitter? = null,

    @Json(name = "parents")
    val parents: List<Parent>? = null,

    @Json(name = "repository")
    val repository: GithubRepo? = null,

    @Json(name = "score")
    val score: Float = 0.toFloat(),

    ) : ResultsItem()

@JsonClass(generateAdapter = true)
data class Parent(

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "html_url")
    val htmlUrl: String? = null,

    @Json(name = "sha")
    val sha: String? = null,

    )

@JsonClass(generateAdapter = true)
data class Tree(

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "sha")
    val sha: String? = null,

    )
