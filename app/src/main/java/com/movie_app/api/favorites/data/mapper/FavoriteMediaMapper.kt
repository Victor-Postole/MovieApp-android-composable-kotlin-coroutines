package com.movie_app.api.favorites.data.mapper

import com.movie_app.api.favorites.data.local.FavoriteMediaEntity
import com.movie_app.api.main.domain.model.Media


fun FavoriteMediaEntity.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = try {
            videosIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaIds = try {
            similarMediaIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}


fun Media.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = try {
            videosIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        similarMediaIds = try {
            similarMediaIds.joinToString(",")
        } catch (e: Exception) {
            ""
        }
    )
}
