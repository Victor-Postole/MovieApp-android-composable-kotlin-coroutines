package com.movie_app.api.categories.presentation

import com.movie_app.api.main.domain.model.Media


data class CategoriesState(

    val actionAndAdventureList: List<Media> = emptyList(),
    val dramaList: List<Media> = emptyList(),
    val comedyList: List<Media> = emptyList(),
    val sciFiAndFantasyList: List<Media> = emptyList(),
    val animationList: List<Media> = emptyList()
)















