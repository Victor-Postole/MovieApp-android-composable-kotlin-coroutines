package com.movie_app.api.details.data.remoteYoutube.dto

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)