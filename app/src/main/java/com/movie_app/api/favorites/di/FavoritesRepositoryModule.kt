package com.movie_app.api.favorites.di

import com.movie_app.api.favorites.data.repository.FavoritesRepositoryImpl
import com.movie_app.api.favorites.domain.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository
}






















