package com.movie_app.api.details.data.remoteYoutube.dto

data class YoutubeDto(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)