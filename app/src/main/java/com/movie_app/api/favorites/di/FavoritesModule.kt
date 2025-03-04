package com.movie_app.api.favorites.di

import android.app.Application
import androidx.room.Room
import com.movie_app.api.favorites.data.local.FavoriteMediaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Provides
    @Singleton
    fun providesMediaDatabase(application: Application): FavoriteMediaDatabase {
        return Room.databaseBuilder(
            application,
            FavoriteMediaDatabase::class.java,
            "favorites-db.db"
        ).build()
    }


}

















