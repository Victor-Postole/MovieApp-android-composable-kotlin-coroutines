package com.movie_app.api.details.domain.repository

import com.movie_app.api.details.data.remoteYoutube.dto.Id


interface YoutubeRepository {
    suspend fun getYoutube(movieName: String): Id?
}
















