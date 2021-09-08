package com.bradthome.android.githubsearch.core.hilt

import com.squareup.moshi.Moshi
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@EntryPoint
interface ApplicationComponent {
    val moshi: Moshi
}