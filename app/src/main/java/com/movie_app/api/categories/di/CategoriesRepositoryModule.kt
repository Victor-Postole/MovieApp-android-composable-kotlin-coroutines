package com.movie_app.api.categories.di

import com.movie_app.api.categories.data.repository.CategoriesRepositoryImpl
import com.movie_app.api.categories.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository

}
























