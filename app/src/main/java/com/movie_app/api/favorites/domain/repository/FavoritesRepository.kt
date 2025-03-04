package com.movie_app.api.favorites.domain.repository

import com.movie_app.api.main.domain.model.Media
import kotlinx.coroutines.flow.Flow


interface FavoritesRepository {

    suspend fun favoritesDbUpdateEventFlow(): Flow<Boolean>

    suspend fun upsetFavoritesMediaItem(media: Media)

    suspend fun deleteFavoritesMediaItem(media: Media)

    suspend fun getMediaItemById(mediaId: Int): Media?

    suspend fun getLikedMediaList(): List<Media>
    suspend fun getBookmarkedMediaList(): List<Media>

}





















