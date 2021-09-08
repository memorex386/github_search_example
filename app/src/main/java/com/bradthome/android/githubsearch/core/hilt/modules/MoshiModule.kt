package com.bradthome.android.githubsearch.core.hilt.modules

import com.bradthome.android.githubsearch.models.SortOptions
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    @Provides
    fun provideMoshi(): Moshi {
        val moshiBuilder = Moshi.Builder()
        moshiBuilder.add(SortOptions.SortOptionsAdapter())
        return moshiBuilder.build()
    }
}