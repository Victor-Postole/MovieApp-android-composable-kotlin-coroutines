package com.movie_app.api.main.data.remote.dto

data class MediaListDto(
    val page: Int? = null,
    val results: List<MediaDto>? = null
)