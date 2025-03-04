package com.movie_app.api.integration.remote


import com.movie_app.api.details.data.remoteYoutube.api.YoutubeDetailsApi
import com.movie_app.api.details.data.remoteYoutube.dto.YoutubeDto
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

class YoutubeDetailsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var youtubeDetailsApi: YoutubeDetailsApi

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

        youtubeDetailsApi = retrofit.create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSearchVideosReturnsValidData() = runBlocking {
        val mockResponse = """
            {
                "items": [
                    {
                        "id": {
                            "videoId": "123"
                        },
                        "snippet": {
                            "title": "Sample Video 1"
                        }
                    },
                    {
                        "id": {
                            "videoId": "456"
                        },
                        "snippet": {
                            "title": "Sample Video 2"
                        }
                    }
                ]
            }
        """
        // Enqueue a mock response with status code 200
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        val response = youtubeDetailsApi.searchVideos("test query", "snippet", "video", "YOUTUBE_API_KEY")

        // Check the result
        assertEquals(2, response?.items?.size)
        assertEquals("Sample Video 1", response?.items?.get(0)?.snippet?.title)
        assertEquals("Sample Video 2", response?.items?.get(1)?.snippet?.title)
    }

    @Test
    fun testSearchVideosReturnsErrorOnInvalidAPIKey(): Unit = runBlocking {
        // Enqueue a mock response with an error code (e.g., Unauthorized)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .setBody("{\"error\": {\"message\": \"Invalid API key.\"}}")
        )

        try {
            youtubeDetailsApi.searchVideos("test query", "snippet", "video", "INVALID_API_KEY")
        } catch (e: HttpException) {
            // Assert that we received an HTTP 401 Unauthorized error
            assertEquals(401, e.code())
        }
    }
}
