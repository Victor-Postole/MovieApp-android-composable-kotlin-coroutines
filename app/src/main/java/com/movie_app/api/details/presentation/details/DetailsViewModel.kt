package com.movie_app.api.details.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie_app.api.details.domain.repository.DetailsRepository
import com.movie_app.api.details.domain.repository.SimilarRepository
import com.movie_app.api.details.domain.repository.VideosRepository
import com.movie_app.api.details.domain.repository.YoutubeRepository
import com.movie_app.api.details.domain.usecase.MinutesToReadableTime
import com.movie_app.api.favorites.domain.repository.FavoritesRepository
import com.movie_app.api.main.domain.repository.MainRepository
import com.movie_app.api.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val detailsRepository: DetailsRepository,
    private val videosRepository: VideosRepository,
    private val similarRepository: SimilarRepository,
    private val youtubeRepository: YoutubeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    private val _navigateToWatchVideoChannel = Channel<String>()
    val navigateToWatchVideoChannel = _navigateToWatchVideoChannel.receiveAsFlow()


    suspend fun onEvent(detailsUiEvent: DetailsUiEvents) {
        when (detailsUiEvent) {
            is DetailsUiEvents.StartLoading -> {
                loadMediaItem(
                    id = detailsUiEvent.mediaId
                )
            }


            is DetailsUiEvents.NavigateToWatchVideo -> {
                viewModelScope.launch {

                    viewModelScope.launch {
                        try {
                            val response = youtubeRepository.getYoutube(detailsUiEvent.media.title)
                            // Update the state with the YouTube video ID
                            _detailsState.update {
                                it.copy(
                                    youtubeVideoId = response?.videoId
                                        ?: "_JGGLJMpVks" // Fallback ID
                                )
                            }

                        } catch (e: Exception) {

                            // Optionally update the state or perform fallback logic
                            _detailsState.update {
                                it.copy(
                                    youtubeVideoId = "_JGGLJMpVks" // Default fallback ID in case of an error
                                )
                            }
                        }
                    }

                }
            }

            DetailsUiEvents.Refresh -> {
                loadSimilar()
            }

            DetailsUiEvents.BookmarkOrUnBookmark -> {
                bookmarkOrUnBookmark()
            }

            DetailsUiEvents.LikeOrDislike -> {
                likeOrDislike()
            }

            is DetailsUiEvents.ShowOrHideAlertDialog -> {
                val media = detailsState.value.media

                if (detailsUiEvent.alertDialogType == 1 && media?.isLiked == false) {
                    likeOrDislike()
                    return
                }

                if (detailsUiEvent.alertDialogType == 2 && media?.isBookmarked == false) {
                    bookmarkOrUnBookmark()
                    return
                }

                _detailsState.update {
                    it.copy(
                        showAlertDialog = !it.showAlertDialog,
                        alertDialogType = detailsUiEvent.alertDialogType
                    )
                }

            }
        }
    }

    private fun loadMediaItem(
        isRefresh: Boolean = false,
        id: Int = detailsState.value.media?.mediaId ?: 0
    ) {

        viewModelScope.launch {
            _detailsState.update {
                it.copy(
                    media = mainRepository.getMediaById(id)
                )
            }

            viewModelScope.launch {
                loadDetails(isRefresh)
                loadVideos(isRefresh)
                loadSimilar()
            }
        }
    }

    private suspend fun loadDetails(
        isRefresh: Boolean
    ) {
        detailsRepository.getDetails(
            id = detailsState.value.media?.mediaId ?: 0,
            isRefreshing = isRefresh
        ).collect { result ->
            when (result) {
                is Resource.Error -> Unit

                is Resource.Loading -> Unit

                is Resource.Success -> {
                    result.data?.let { media ->
                        _detailsState.update {
                            it.copy(
                                media = it.media?.copy(
                                    runTime = media.runTime,
                                    tagLine = media.tagLine
                                ),
                                readableTime = if (media.runTime != 0) {
                                    MinutesToReadableTime(media.runTime).invoke()
                                } else ""
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadVideos(
        isRefresh: Boolean
    ) {
        videosRepository.getVideos(
            id = detailsState.value.media?.mediaId ?: 0,
            isRefreshing = isRefresh
        ).collect { result ->
            when (result) {
                is Resource.Error -> Unit

                is Resource.Loading -> Unit

                is Resource.Success -> {
                    result.data?.let { videoList ->
                        _detailsState.update {
                            it.copy(
                                videoList = videoList,
                                videoId = if (videoList.isNotEmpty()) {
                                    videoList.shuffled()[0]
                                } else {
                                    ""
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadSimilar(isRefresh: Boolean = false) {
        similarRepository.getSimilarMedia(
            isRefresh,
            id = detailsState.value.media?.mediaId ?: 0
        ).collect { result ->
            when (result) {
                is Resource.Error -> Unit

                is Resource.Loading -> Unit

                is Resource.Success -> {
                    result.data?.let { similarMediaList ->
                        _detailsState.update {
                            it.copy(
                                similarList = similarMediaList
                            )
                        }
                    }
                }
            }
        }
    }

    private fun likeOrDislike() {
        _detailsState.update {
            it.copy(
                media = it.media?.copy(
                    isLiked = !it.media.isLiked
                ),
                alertDialogType = 0,
                showAlertDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun bookmarkOrUnBookmark() {
        _detailsState.update {
            it.copy(
                media = it.media?.copy(
                    isBookmarked = !it.media.isBookmarked
                ),
                alertDialogType = 0,
                showAlertDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun updateOrDeleteMedia() {
        viewModelScope.launch {
            detailsState.value.media?.let { media ->
                if (!media.isLiked && !media.isBookmarked) {
                    favoritesRepository.deleteFavoritesMediaItem(
                        media
                    )
                } else {
                    mainRepository.upsertMediaItem(
                        media
                    )
                    favoritesRepository.upsetFavoritesMediaItem(
                        media
                    )
                }
            }
        }
    }

}






















