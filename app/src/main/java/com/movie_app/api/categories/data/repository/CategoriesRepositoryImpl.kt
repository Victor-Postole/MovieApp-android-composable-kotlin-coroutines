package com.movie_app.api.categories.data.repository

import com.movie_app.api.categories.domain.repository.CategoriesRepository
import com.movie_app.api.main.data.local.MediaDatabase
import com.movie_app.api.main.data.mappers.toMedia
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.util.APIConstants.genres
import javax.inject.Inject


class CategoriesRepositoryImpl @Inject constructor(
    mediaDatabase: MediaDatabase
) : CategoriesRepository {

    private val mediaDao = mediaDatabase.mediaDao

    override suspend fun getActionAndAdventure(): List<Media> {
        val actionAndAdventureGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Adventure",
                "Horror",
                "Action",
                "Western",
                "Thriller",
                "Crime",
                "War"
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(actionAndAdventureGenres)
    }

    override suspend fun getDrama(): List<Media> {
        val dramaGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Drama",
                "Comedy",
                "Family",
                "Romance",
                "Music"
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(dramaGenres)
    }

    override suspend fun getComedy(): List<Media> {
        val comedyGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Comedy",
                "Family",
                "Romance"
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(comedyGenres)
    }

    override suspend fun getSciFiAndFantasy(): List<Media> {
        val sciFiAndFantasyGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Fantasy",
                "Horror",
                "Thriller",
                "Crime",
                "Documentary",
                "Science Fiction",
                "Mystery",
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(sciFiAndFantasyGenres)
    }

    override suspend fun getAnimation(): List<Media> {
        val animationGenres = genres.filter { genre ->
            genre.genreName == "Animation"
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(animationGenres)
    }

    private suspend fun getMediaListByGenreIds(
        genreIds: List<String>
    ): List<Media> {

        val mediaList = mediaDao.getAllMediaList().map {
            it.toMedia()
        }

        return mediaList.filter { media ->
            media.genreIds.any { genreId ->
                genreId in genreIds
            }
        }.shuffled()

    }
}