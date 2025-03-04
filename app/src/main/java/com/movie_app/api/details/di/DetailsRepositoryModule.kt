package com.movie_app.api.details.di

import com.movie_app.api.details.data.repository.DetailsRepositoryImpl
import com.movie_app.api.details.data.repository.SimilarRepositoryImpl
import com.movie_app.api.details.data.repository.VideosRepositoryImpl
import com.movie_app.api.details.data.repository.YoutoubeRepositoryImpl
import com.movie_app.api.details.domain.repository.DetailsRepository
import com.movie_app.api.details.domain.repository.SimilarRepository
import com.movie_app.api.details.domain.repository.VideosRepository
import com.movie_app.api.details.domain.repository.YoutubeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindVideosRepository(videosRepositoryImpl: VideosRepositoryImpl): VideosRepository

    @Binds
    @Singleton
    abstract fun bindSimilarRepository(similarRepositoryImpl: SimilarRepositoryImpl): SimilarRepository

    @Binds
    @Singleton
    abstract fun bindYoutubeRepository(youtubeRepositoryImpl: YoutoubeRepositoryImpl): YoutubeRepository
}
























