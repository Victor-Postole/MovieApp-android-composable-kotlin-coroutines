package com.movie_app.api.favorites.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteMediaEntity::class], version = 1)
abstract class FavoriteMediaDatabase : RoomDatabase() {
    abstract val favoriteMediaDao: FavoriteMediaDao
}















