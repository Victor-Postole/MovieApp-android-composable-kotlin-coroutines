package com.movie_app.api.details.data.remoteYoutube.api

import com.movie_app.api.BuildConfig
import com.movie_app.api.details.data.remoteYoutube.dto.YoutubeDto
import retrofit2.http.GET
import retrofit2.http.Query

sealed interface YoutubeDetailsApi {


    @GET("search")
    suspend fun searchVideos(
        @Query("q") searchQuery: String,
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video",
        @Query("key") apiKey: String = YOUTUBE_API_KEY
    ): YoutubeDto?

    companion object {
        const val YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/"
        const val YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY
    }
}