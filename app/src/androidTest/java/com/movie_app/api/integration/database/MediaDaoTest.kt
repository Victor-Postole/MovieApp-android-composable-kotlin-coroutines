package com.movie_app.api.integration.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.movie_app.api.main.data.local.MediaDao
import com.movie_app.api.main.data.local.MediaDatabase
import com.movie_app.api.main.data.local.MediaEntity
import kotlinx.coroutines.runBlocking
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class MediaDaoTest {

    private lateinit var mediaDao: MediaDao
    private lateinit var db: MediaDatabase

    @Before
    fun setup() {
        // Initialize the in-memory database and dao
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MediaDatabase::class.java)
            .allowMainThreadQueries()  // Allow queries on the main thread for testing
            .build()
        mediaDao = db.mediaDao
    }

    @After
    fun tearDown() {
        // Close the database after each test
        db.close()
    }

    @Test
    fun testUpsertMediaItem() = runBlocking {
        // Prepare test data with updated fields
        val mediaEntity = MediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/path/to/backdrop",
            genreIds = "1,2,3",  // List represented as String for this test
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Movie overview",
            popularity = 7.5,
            posterPath = "/path/to/poster",
            releaseDate = "2025-01-01",
            title = "Movie Title",
            voteAverage = 8.0,
            voteCount = 1000,
            category = "action",
            runTime = 120,
            tagLine = "An epic movie",
            videosIds = "101,102,103",  // List of video IDs
            similarMediaIds = "201,202,203"  // Similar media IDs
        )

        // Upsert media item
        mediaDao.upsertMediaItem(mediaEntity)

        // Verify it was upserted correctly
        val retrievedMedia = mediaDao.getMediaItemById(mediaEntity.mediaId)
        assertEquals(mediaEntity, retrievedMedia)
    }

    @Test
    fun testGetAllMediaList() = runBlocking {
        // Prepare test data with updated fields
        val mediaList = listOf(
            MediaEntity(
                mediaId = 1,
                isLiked = true,
                isBookmarked = true,
                adult = false,
                backdropPath = "/path1",
                genreIds = "1,2",
                mediaType = "movie",
                originCountry = "US",
                originalLanguage = "en",
                originalTitle = "Movie 1",
                overview = "Overview of Movie 1",
                popularity = 8.0,
                posterPath = "/poster1",
                releaseDate = "2025-01-01",
                title = "Title 1",
                voteAverage = 7.5,
                voteCount = 500,
                category = "comedy",
                runTime = 90,
                tagLine = "Tagline 1",
                videosIds = "101",
                similarMediaIds = "201"
            ),
            MediaEntity(
                mediaId = 2,
                isLiked = false,
                isBookmarked = true,
                adult = false,
                backdropPath = "/path2",
                genreIds = "2,3",
                mediaType = "tv show",
                originCountry = "US",
                originalLanguage = "en",
                originalTitle = "Movie 2",
                overview = "Overview of Movie 2",
                popularity = 6.5,
                posterPath = "/poster2",
                releaseDate = "2025-02-01",
                title = "Title 2",
                voteAverage = 7.0,
                voteCount = 400,
                category = "drama",
                runTime = 60,
                tagLine = "Tagline 2",
                videosIds = "102",
                similarMediaIds = "202"
            )
        )

        // Insert data into the database
        mediaDao.upsertMediaList(mediaList)

        // Retrieve all media items
        val result = mediaDao.getAllMediaList()

        // Verify the result
        assertEquals(mediaList, result)
    }

    @Test
    fun testDoesMediaItemExist() = runBlocking {
        // Prepare test data with updated fields
        val mediaEntity = MediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/path/to/backdrop",
            genreIds = "1,2,3",  // List represented as String for this test
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Movie overview",
            popularity = 7.5,
            posterPath = "/path/to/poster",
            releaseDate = "2025-01-01",
            title = "Movie Title",
            voteAverage = 8.0,
            voteCount = 1000,
            category = "action",
            runTime = 120,
            tagLine = "An epic movie",
            videosIds = "101,102,103",  // List of video IDs
            similarMediaIds = "201,202,203"  // Similar media IDs
        )

        // Insert data
        mediaDao.upsertMediaItem(mediaEntity)

        // Check if the media item exists
        val count = mediaDao.doesMediaItemExists(mediaEntity.mediaId)
        assertEquals(1, count)  // Should return 1 if it exists
    }

    @Test
    fun testDeleteMediaItem() = runBlocking {
        // Prepare test data with updated fields
        val mediaEntity = MediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/path/to/backdrop",
            genreIds = "1,2,3",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Movie overview",
            popularity = 7.5,
            posterPath = "/path/to/poster",
            releaseDate = "2025-01-01",
            title = "Movie Title",
            voteAverage = 8.0,
            voteCount = 1000,
            category = "action",
            runTime = 120,
            tagLine = "An epic movie",
            videosIds = "101,102,103",
            similarMediaIds = "201,202,203"
        )

        // Insert the media item
        mediaDao.upsertMediaItem(mediaEntity)

        // Delete the media item
        mediaDao.deleteAllMediaItem()

        // Verify it was deleted
        val mediaList = mediaDao.getAllMediaList()
        assert(mediaList.isEmpty())  // Should be empty after deletion
    }
}
