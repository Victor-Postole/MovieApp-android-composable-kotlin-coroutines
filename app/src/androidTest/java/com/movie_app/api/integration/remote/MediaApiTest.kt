package com.movie_app.api.integration.remote

import com.movie_app.api.main.data.remote.api.MediaApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer


import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.HttpException
import java.net.HttpURLConnection


class MediaApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var mediaApi: MediaApi

    @Before
    fun setup() {
        // Set up MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up Retrofit with MockWebServer URL
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Using MockWebServer's URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mediaApi = retrofit.create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
     fun testGetTrendingReturnsValidData() = runBlocking {
        val mockResponse = """
            {
                "results": [
                    {
                        "id": 1,
                        "title": "Movie 1"
                    },
                    {
                        "id": 2,
                        "title": "Movie 2"
                    }
                ]
            }
        """
        // Enqueue a mock response with status code 200
        mockWebServer.enqueue(
            MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(mockResponse))

        val response = mediaApi.getTrending("movie", "day", 1, "API_KEY")

        // Check the result
        assertEquals(2, response?.results?.size)
        assertEquals("Movie 1", response?.results?.get(0)?.title)
        assertEquals("Movie 2", response?.results?.get(1)?.title)
    }

    @Test
     fun testGetMoviesAndTvReturnsValidData() = runBlocking {
        val mockResponse = """
            {
                "results": [
                    {
                        "id": 1,
                        "title": "Movie 1"
                    },
                    {
                        "id": 2,
                        "title": "TV Show 1"
                    }
                ]
            }
        """
        // Enqueue a mock response with status code 200
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(mockResponse))

        val response = mediaApi.getMoviesAndTv("movie", "popular", 1, "API_KEY")

        // Check the result
        assertEquals(2, response?.results?.size)
        assertEquals("Movie 1", response?.results?.get(0)?.title)
        assertEquals("TV Show 1", response?.results?.get(1)?.title)
    }

    @Test
     fun testGetTrendingReturnsErroronInvalidAPIkey(): Unit = runBlocking {
        // Enqueue a mock response with an error code (e.g., Unauthorized)
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody("{\"status_code\": 7, \"status_message\": \"Invalid API key.\"}"))

        try {
            mediaApi.getTrending("movie", "week", 1, "INVALID_API_KEY")
        } catch (e: HttpException) {
            // Assert that we received an HTTP 401 Unauthorized error
            assertEquals(401, e.code())
        }
    }
}
