package com.movie_app.api.search.peresentation

import com.movie_app.api.main.domain.model.Media


sealed class SearchUiEvents {
    data class OnSearchQueryChange(
        val searchQuery: String
    ) : SearchUiEvents()

    data class OnSearchItemClick(
        val media: Media
    ) : SearchUiEvents()

    object OnPaginate : SearchUiEvents()
}






















