package com.movie_app.api.favorites.presentation

import com.movie_app.api.main.domain.model.Media


data class FavoritesState(
    val likedList: List<Media> = emptyList(),
    val bookmarksList: List<Media> = emptyList()
)