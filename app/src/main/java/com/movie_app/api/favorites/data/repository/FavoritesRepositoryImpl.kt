package com.movie_app.api.favorites.data.repository

import com.movie_app.api.favorites.data.local.FavoriteMediaDatabase
import com.movie_app.api.favorites.data.mapper.toFavoriteMediaEntity
import com.movie_app.api.favorites.data.mapper.toMedia
import com.movie_app.api.favorites.domain.repository.FavoritesRepository
import com.movie_app.api.main.data.local.MediaDatabase
import com.movie_app.api.main.domain.model.Media
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


class FavoritesRepositoryImpl @Inject constructor(
    favoriteMediaDatabase: FavoriteMediaDatabase,
    mediaDatabase: MediaDatabase
) : FavoritesRepository {

    private val mediaDao = mediaDatabase.mediaDao
    private val favoriteMediaDao = favoriteMediaDatabase.favoriteMediaDao

    private val _favoritesDbUpdateEventChannel = Channel<Boolean>()
    override suspend fun favoritesDbUpdateEventFlow(): Flow<Boolean> =
        _favoritesDbUpdateEventChannel.receiveAsFlow()

    override suspend fun upsetFavoritesMediaItem(media: Media) {
        favoriteMediaDao.upsertFavoriteMediaItem(
            media.toFavoriteMediaEntity()
        )

        _favoritesDbUpdateEventChannel.send(true)
    }

    override suspend fun deleteFavoritesMediaItem(media: Media) {
        favoriteMediaDao.deleteFavoriteMediaItem(
            media.toFavoriteMediaEntity()
        )

        _favoritesDbUpdateEventChannel.send(true)
    }

    override suspend fun getMediaItemById(mediaId: Int): Media? {
        return favoriteMediaDao.getFavoriteMediaItemById(
            mediaId
        )?.toMedia()
    }

    override suspend fun getLikedMediaList(): List<Media> {
        val likedList = favoriteMediaDao.getLikedMediaList()

        if (likedList.isNotEmpty()) {
            return likedList.map { it.toMedia() }
        }

        return emptyList()
    }

    override suspend fun getBookmarkedMediaList(): List<Media> {
        val bookmarkedList = favoriteMediaDao.getBookmarkedList()

        if (bookmarkedList.isNotEmpty()) {
            return bookmarkedList.map { it.toMedia() }
        }

        return emptyList()
    }

}





















