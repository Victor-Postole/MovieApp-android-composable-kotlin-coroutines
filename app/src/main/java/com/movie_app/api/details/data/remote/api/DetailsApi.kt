package com.movie_app.api.details.data.remote.api

import com.movie_app.api.details.data.remote.dto.DetailsDto
import com.movie_app.api.details.data.remote.dto.VideosDto
import com.movie_app.api.main.data.remote.api.MediaApi
import com.movie_app.api.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DetailsApi {

    @GET("{type}/{id}")
    suspend fun getDetails(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY,
    ): DetailsDto?

    @GET("{type}/{id}/videos")
    suspend fun getVideos(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY,
    ): VideosDto?

    @GET("{type}/{id}/similar")
    suspend fun getSimilarMediaList(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY,
    ): MediaListDto?

}




















