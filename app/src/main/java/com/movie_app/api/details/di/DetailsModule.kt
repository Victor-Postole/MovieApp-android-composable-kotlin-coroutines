package com.movie_app.api.details.di

import com.movie_app.api.details.data.remote.api.DetailsApi
import com.movie_app.api.details.data.remoteYoutube.api.YoutubeDetailsApi
import com.movie_app.api.details.data.remoteYoutube.api.YoutubeDetailsApi.Companion.YOUTUBE_BASE_URL
import com.movie_app.api.main.data.remote.api.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DetailsModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()


    @Provides
    @Singleton
    fun providesMediaApi(): DetailsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MediaApi.BASE_URL)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesYoutubeApi(): YoutubeDetailsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(YOUTUBE_BASE_URL)
            .client(client)
            .build()
            .create()
    }

}

















