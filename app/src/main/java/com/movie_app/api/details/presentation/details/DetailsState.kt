package com.movie_app.api.details.presentation.details

import com.movie_app.api.main.domain.model.Media


data class DetailsState(
    val media: Media? = null,
    val videoId: String = "",
    val youtubeVideoId: String = "",
    val videoName: String = "",
    val readableTime: String = "",

    val videoList: List<String> = emptyList(),
    val similarList: List<Media> = emptyList(),

    val showAlertDialog: Boolean = false,
    val alertDialogType: Int = 0
)
























