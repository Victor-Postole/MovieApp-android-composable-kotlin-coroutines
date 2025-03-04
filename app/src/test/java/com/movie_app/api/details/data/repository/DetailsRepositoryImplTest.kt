package com.movie_app.api.details.data.repository

import android.app.Application
import com.movie_app.api.details.data.remote.api.DetailsApi
import com.movie_app.api.details.data.remote.dto.DetailsDto
import com.movie_app.api.details.domain.repository.DetailsRepository
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.main.domain.repository.MainRepository
import com.movie_app.api.util.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class DetailsRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var detailsRepository: DetailsRepository
    private lateinit var detailsApi: DetailsApi
    private lateinit var mainRepository: MainRepository
    private lateinit var application: Application

    @Before
    fun setup() {
        // Set up MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Mock dependencies
        detailsApi = mock(DetailsApi::class.java)
        mainRepository = mock(MainRepository::class.java)
        application = mock(Application::class.java)

        // Create the repository instance
        detailsRepository = DetailsRepositoryImpl(detailsApi, mainRepository, application)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetDetails_LocalDetailsAvailable_ReturnsSuccess(): Unit = runBlocking {
        // Create a mock Media object
        val media = Media(
            mediaId = 1,
            isLiked = false,
            isBookmarked = false,
            adult = false,
            backdropPath = "backdrop.jpg",
            genreIds = listOf("Action", "Adventure"),
            mediaType = "movie",
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Some overview",
            popularity = 10.5,
            posterPath = "poster.jpg",
            releaseDate = "2022-01-01",
            title = "Movie Title",
            voteAverage = 8.0,
            voteCount = 1000,
            category = "Action",
            runTime = 120,
            tagLine = "A great movie",
            videosIds = listOf("vid1", "vid2"),
            similarMediaIds = listOf(2, 3)
        )

        `when`(mainRepository.getMediaById(1)).thenReturn(media)

        // Simulate the response from getDetails
        val flow = detailsRepository.getDetails(1, isRefreshing = false)

        // Collect the emitted values
        flow.collect { resource ->
            if (resource is Resource.Success) {
                // Verify the expected result
                assert(resource.data == media)
            }
        }

        // Verify that the main repository was called to fetch the media
        verify(mainRepository).getMediaById(1)
    }

    @Test
    fun testGetDetails_FetchFromRemote_ReturnsSuccess() = runBlocking {
        // Create a mock Media object
        val media = Media(
            mediaId = 1,
            isLiked = false,
            isBookmarked = false,
            adult = false,
            backdropPath = "backdrop.jpg",
            genreIds = listOf("Action", "Adventure"),
            mediaType = "movie",
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Some overview",
            popularity = 10.5,
            posterPath = "poster.jpg",
            releaseDate = "2022-01-01",
            title = "Movie Title",
            voteAverage = 8.0,
            voteCount = 1000,
            category = "Action",
            runTime = 0,
            tagLine = "",
            videosIds = listOf("vid1", "vid2"),
            similarMediaIds = listOf(2, 3)
        )

        val remoteDetailsDto = DetailsDto(runtime = 120, tagline = "Updated movie")
        val updatedMedia = media.copy(runTime = remoteDetailsDto.runtime ?: 0, tagLine = remoteDetailsDto.tagline ?: "")

        `when`(mainRepository.getMediaById(1)).thenReturn(media)
        `when`(detailsApi.getDetails("movie", 1)).thenReturn(remoteDetailsDto)
        `when`(mainRepository.upsertMediaItem(updatedMedia)).thenReturn(Unit)
        `when`(mainRepository.getMediaById(1)).thenReturn(updatedMedia)

        // Simulate the response from getDetails
        val flow = detailsRepository.getDetails(1, isRefreshing = true)

        // Collect the emitted values
        flow.collect { resource ->
            if (resource is Resource.Success) {
                // Verify the expected result
                assert(resource.data == updatedMedia)
            }
        }

        // Verify that API was called to fetch details
        verify(detailsApi).getDetails("movie", 1)
        verify(mainRepository).upsertMediaItem(updatedMedia)
    }
}
