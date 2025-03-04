package com.movie_app.api.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.main.domain.repository.MainRepository
import com.movie_app.api.util.APIConstants.ALL
import com.movie_app.api.util.APIConstants.MOVIE
import com.movie_app.api.util.APIConstants.POPULAR
import com.movie_app.api.util.APIConstants.TRENDING_TIME
import com.movie_app.api.util.APIConstants.TV
import com.movie_app.api.util.Resource
import com.movie_app.api.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        loadTrending()
        loadTv()
        loadMovies()
        loadSimilar()
    }

    suspend fun event(mainUiEvents: MainUiEvents) {

        when (mainUiEvents) {
            is MainUiEvents.Paginate -> {
                when (mainUiEvents.route) {
                    Screen.Trending.route -> {
                        loadTrending(
                            forceFetchFromRemote = true
                        )
                    }

                    Screen.Tv.route -> {
                        loadTv(
                            forceFetchFromRemote = true
                        )
                    }

                    Screen.Movies.route -> {
                        loadMovies(
                            forceFetchFromRemote = true
                        )
                    }

                    Screen.Similar.route -> {
                        loadSimilar(
                            isRefresh = true
                        )
                    }
                }
            }

            is MainUiEvents.Refresh -> {
                when (mainUiEvents.route) {
                    Screen.Main.route -> {
                        _mainState.update {
                            it.copy(specialList = emptyList())
                        }

                        loadTrending(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        loadTv(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        loadMovies(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screen.Trending.route -> {
                        loadTrending(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screen.Tv.route -> {
                        loadTv(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screen.Movies.route -> {
                        loadMovies(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }

                    Screen.Similar.route -> {
                        loadSimilar(true)
                    }
                }
            }
        }
    }

    private fun loadSpecial(list: List<Media> = emptyList()) {
        if (mainState.value.specialList.size <= 4) {
            _mainState.update {
                it.copy(
                    specialList = it.specialList + list.take(2)
                )
            }
        }
    }

    private fun loadTrending(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false,
    ) {

        viewModelScope.launch {
            mainRepository.getTrending(
                forceFetchFromRemote,
                isRefresh,
                ALL,
                TRENDING_TIME,
                mainState.value.trendingPage
            ).collect { result ->

                when (result) {
                    is Resource.Error -> Unit

                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { mediaList ->
                            val shuffledList = mediaList.shuffled()



                            if (isRefresh) {
                                _mainState.update {
                                    it.copy(
                                        trendingList = shuffledList,
                                        trendingPage = 2
                                    )
                                }
                                loadSpecial(shuffledList)
                            } else {
                                _mainState.update {
                                    it.copy(
                                        trendingList = it.trendingList + shuffledList,
                                        trendingPage = it.trendingPage + 1
                                    )
                                }

                                if (!forceFetchFromRemote) {
                                    loadSpecial(shuffledList)
                                }
                            }
                        }
                    }
                }

            }
        }
    }


    private fun loadTv(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false,
    ) {

        viewModelScope.launch {
            mainRepository.getMoviesAndTv(
                forceFetchFromRemote,
                isRefresh,
                TV,
                POPULAR,
                mainState.value.tvPage
            ).collect { result ->

                when (result) {
                    is Resource.Error -> Unit

                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { mediaList ->
                            val shuffledList = mediaList.shuffled()

                            if (isRefresh) {
                                _mainState.update {
                                    it.copy(
                                        tvList = shuffledList,
                                        tvPage = 2
                                    )
                                }
                                loadSpecial(shuffledList)
                            } else {
                                _mainState.update {
                                    it.copy(
                                        tvList = it.tvList + shuffledList,
                                        tvPage = it.tvPage + 1
                                    )
                                }

                                if (!forceFetchFromRemote) {
                                    loadSpecial(shuffledList)
                                }
                            }
                        }
                    }
                }

            }
        }
    }


    private fun loadMovies(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false,
    ) {

        viewModelScope.launch {
            mainRepository.getMoviesAndTv(
                forceFetchFromRemote,
                isRefresh,
                MOVIE,
                POPULAR,
                mainState.value.moviesPage
            ).collect { result ->

                when (result) {
                    is Resource.Error -> Unit

                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { mediaList ->
                            val shuffledList = mediaList.shuffled()

                            if (isRefresh) {
                                _mainState.update {
                                    it.copy(
                                        moviesList = shuffledList,
                                        moviesPage = 2
                                    )
                                }
                                loadSpecial(shuffledList)
                            } else {
                                _mainState.update {
                                    it.copy(
                                        moviesList = it.moviesList + shuffledList,
                                        moviesPage = it.moviesPage + 1
                                    )
                                }

                                if (!forceFetchFromRemote) {
                                    loadSpecial(shuffledList)
                                } else {

                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun loadSimilar(isRefresh: Boolean = false) {
        viewModelScope.launch {
            val similarMedia = mainRepository.getAllMediaList().shuffled()

            Log.e("similarMedia", similarMedia.toString())

            _mainState.update {
                it.copy(
                    similarList = similarMedia,
                    moviesPage = 2
                )
            }


        }
    }


}























