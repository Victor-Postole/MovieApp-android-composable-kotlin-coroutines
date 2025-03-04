package com.movie_app.api.details.presentation.details

import com.movie_app.api.main.domain.model.Media


sealed class DetailsUiEvents {
    data class StartLoading(
        val mediaId: Int
    ) : DetailsUiEvents()

    object Refresh : DetailsUiEvents()
    data class NavigateToWatchVideo(val media: Media) : DetailsUiEvents()

    data class ShowOrHideAlertDialog(
        val alertDialogType: Int = 0
    ) : DetailsUiEvents()

    object LikeOrDislike : DetailsUiEvents()
    object BookmarkOrUnBookmark : DetailsUiEvents()
}




















