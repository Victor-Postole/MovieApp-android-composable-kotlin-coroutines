package com.movie_app.api.search.domain.repository

import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.util.Resource
import kotlinx.coroutines.flow.Flow


interface SearchRepository {

    suspend fun getSearchList(
        query: String,
        page: Int
    ): Flow<Resource<List<Media>>>

}















