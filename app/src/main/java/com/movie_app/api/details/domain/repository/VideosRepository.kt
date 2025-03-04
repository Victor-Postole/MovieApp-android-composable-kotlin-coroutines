package com.movie_app.api.details.domain.repository

import com.movie_app.api.util.Resource
import kotlinx.coroutines.flow.Flow


interface VideosRepository {
    suspend fun getVideos(id: Int, isRefreshing: Boolean): Flow<Resource<List<String>>>
}
















