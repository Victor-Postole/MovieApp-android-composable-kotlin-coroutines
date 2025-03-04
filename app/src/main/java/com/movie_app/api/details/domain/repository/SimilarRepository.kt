package com.movie_app.api.details.domain.repository

import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.util.Resource
import kotlinx.coroutines.flow.Flow


interface SimilarRepository {
    suspend fun getSimilarMedia(isRefresh: Boolean, id: Int): Flow<Resource<List<Media>>>
}
















