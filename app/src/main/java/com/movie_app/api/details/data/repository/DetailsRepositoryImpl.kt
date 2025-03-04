package com.movie_app.api.details.data.repository

import android.app.Application
import com.movie_app.api.R
import com.movie_app.api.details.data.remote.api.DetailsApi
import com.movie_app.api.details.data.remote.dto.DetailsDto
import com.movie_app.api.details.domain.repository.DetailsRepository
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.main.domain.repository.MainRepository
import com.movie_app.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val mainRepository: MainRepository,
    private val application: Application
) : DetailsRepository {

    override suspend fun getDetails(id: Int, isRefreshing: Boolean): Flow<Resource<Media>> {
        return flow {
            emit(Resource.Loading(true))

            val media = mainRepository.getMediaById(id)

            val doDetailsExist = media?.runTime != 0 || media.tagLine.isNotEmpty()

            if (doDetailsExist && !isRefreshing) {
                emit(Resource.Success(media))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteDetailsDto =
                media?.let { it?.mediaType?.let { it1 -> getRemoteDetails(type = it1, id = id) } }

            remoteDetailsDto?.let { detailsDto ->

                val mediaWithDetails = media.copy(
                    runTime = detailsDto.runtime ?: 0,
                    tagLine = detailsDto.tagline ?: ""
                )

                mainRepository.upsertMediaItem(mediaWithDetails)

                emit(Resource.Success(mainRepository.getMediaById(id)))
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

    private suspend fun getRemoteDetails(type: String, id: Int): DetailsDto? {

        val remoteDetails = try {
            detailsApi.getDetails(type, id)
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

        return remoteDetails
    }

}






















