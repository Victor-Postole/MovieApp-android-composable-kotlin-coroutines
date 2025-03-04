package com.movie_app.api.main.domain.usecase

import com.movie_app.api.util.APIConstants.genres

/**
 * @author Ahmed Guedmioui
 */
object GenreIdsToString {
    fun genreIdsToString(genreIds: List<String>): String {
        return genreIds.map { id ->
            genres.find { genre ->
                genre.genreId.toString() == id
            }?.genreName
        }.joinToString(" - ")
    }
}