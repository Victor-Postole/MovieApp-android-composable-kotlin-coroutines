package com.movie_app.api.search.peresentation

import com.movie_app.api.main.domain.model.Media


data class SearchState(
    val isLoading: Boolean = false,
    val searchPage: Int = 1,
    val searchQuery: String = "",
    val searchList: List<Media> = emptyList()
)





















