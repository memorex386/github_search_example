package com.bradthome.android.githubsearch.core.hilt.modules

import android.content.Context
import android.util.Log
import com.bradthome.android.githubsearch.network.GithubApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val GIHUB_URL = "https://api.github.com"

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.i("HttpLog", message) })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return loggingInterceptor
    }

    @Provides
    fun provideCacheFile(@ApplicationContext context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    fun provideHttpCache(cacheFile: File): Cache {
        val cacheSize = 10L * 1024L * 1024L
        return Cache(cacheFile, cacheSize)
    }

    @Provides
    fun provideCacheLengthInterceptor(): Interceptor = Interceptor { chain ->
        val maxAge = 60 * 5 // read from cache for 5 minutes
        val response = chain.proceed(chain.request())
        if (response.isSuccessful)
            response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build()
        else response
    }

    @GithubOkHttpClient
    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cacheInterceptor: Interceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    fun provideRetrofit(@GithubOkHttpClient oktHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .client(oktHttpClient)
        .baseUrl("https://api.github.com") //FIXME
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    @Provides
    fun provideApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)


}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubOkHttpClient