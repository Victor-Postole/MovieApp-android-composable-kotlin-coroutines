package com.movie_app.api.details.data.repository

import android.app.Application
import com.movie_app.api.R
import com.movie_app.api.details.data.remote.api.DetailsApi
import com.movie_app.api.details.domain.repository.SimilarRepository
import com.movie_app.api.favorites.domain.repository.FavoritesRepository
import com.movie_app.api.main.data.mappers.toMedia
import com.movie_app.api.main.data.remote.dto.MediaDto
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.main.domain.repository.MainRepository
import com.movie_app.api.util.APIConstants
import com.movie_app.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SimilarRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val mainRepository: MainRepository,
    private val application: Application,
    private val favoritesRepository: FavoritesRepository
) : SimilarRepository {
    override suspend fun getSimilarMedia(isRefresh: Boolean, id: Int): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val media = mainRepository.getMediaById(id)
            val localSimilarList = mainRepository.getMediaListByIds(
                media.similarMediaIds
            )

            if (localSimilarList.isNotEmpty() && !isRefresh) {
                emit(Resource.Success(localSimilarList.shuffled()))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteSimilarList = getRemoteSimilarList(
                type = media.mediaType,
                id = id
            )?.shuffled()

            remoteSimilarList?.let { similarMediaDtos ->
                val similarIds = similarMediaDtos.map { it.id ?: 0 }
                mainRepository.upsertMediaItem(
                    media.copy(
                        similarMediaIds = similarIds
                    )
                )


                val similarMedia = similarMediaDtos.map { mediaDto ->

                    val favoriteMedia =
                        favoritesRepository.getMediaItemById(
                            mediaDto.id ?: 0
                        )

                    mediaDto.toMedia(
                        APIConstants.POPULAR,
                        isLiked = favoriteMedia?.isLiked ?: false,
                        isBookmarked = favoriteMedia?.isBookmarked ?: false,
                    )
                }

                mainRepository.upsertMediaList(similarMedia)

                emit(
                    Resource.Success(
                        mainRepository.getMediaListByIds(similarIds).shuffled()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(
                Resource.Error(
                    application.getString(R.string.couldn_t_load_data)
                )
            )
            emit(Resource.Loading(false))
        }
    }

    private suspend fun getRemoteSimilarList(type: String, id: Int): List<MediaDto>? {

        val remoteDetails = try {
            detailsApi.getSimilarMediaList(type, id, 1)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return remoteDetails?.results
    }

}




















