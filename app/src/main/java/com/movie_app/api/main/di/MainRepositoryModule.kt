package com.movie_app.api.main.di

import com.movie_app.api.main.data.repository.MainRepositoryImpl
import com.movie_app.api.main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

}























