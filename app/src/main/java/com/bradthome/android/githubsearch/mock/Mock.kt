package com.bradthome.android.githubsearch.mock

import android.content.Context
import com.bradthome.android.githubsearch.models.RepositoriesResponse
import com.squareup.moshi.Moshi

class Mock {
    fun repos(context: Context, moshi: Moshi) = moshi.adapter(RepositoriesResponse::class.java)
        .fromJson(context.assets.open("repos.json").bufferedReader().use { it.readText() })
}

