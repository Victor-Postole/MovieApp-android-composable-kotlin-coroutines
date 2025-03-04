package com.movie_app.api.categories.domain.repository

import com.movie_app.api.main.domain.model.Media


interface CategoriesRepository {

    suspend fun getActionAndAdventure(): List<Media>
    suspend fun getDrama(): List<Media>
    suspend fun getComedy(): List<Media>
    suspend fun getSciFiAndFantasy(): List<Media>
    suspend fun getAnimation(): List<Media>

}


















