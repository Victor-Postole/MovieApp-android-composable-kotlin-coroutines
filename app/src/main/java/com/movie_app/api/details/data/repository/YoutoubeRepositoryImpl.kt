package com.movie_app.api.details.data.repository

import com.movie_app.api.details.data.remoteYoutube.api.YoutubeDetailsApi
import com.movie_app.api.details.data.remoteYoutube.dto.Id
import com.movie_app.api.details.domain.repository.YoutubeRepository
import javax.inject.Inject


class YoutoubeRepositoryImpl @Inject constructor(
    private val youtubeDetailsApi: YoutubeDetailsApi,
) : YoutubeRepository {


    override suspend fun getYoutube(movieName: String): Id? {
        val response = youtubeDetailsApi.searchVideos(movieName)?.items?.get(0)?.id

        return response

    }


}

