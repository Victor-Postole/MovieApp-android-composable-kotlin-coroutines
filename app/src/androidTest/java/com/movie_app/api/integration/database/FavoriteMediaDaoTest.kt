package com.movie_app.api.integration.database

import com.movie_app.api.favorites.data.local.FavoriteMediaDao
import com.movie_app.api.favorites.data.local.FavoriteMediaDatabase
import com.movie_app.api.favorites.data.local.FavoriteMediaEntity


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteMediaDaoTest {

    private lateinit var db: FavoriteMediaDatabase
    private lateinit var favoriteMediaDao: FavoriteMediaDao

    @Before
    fun setup() {
        // Initialize the in-memory database for testing
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            FavoriteMediaDatabase::class.java
        ).build()

        favoriteMediaDao = db.favoriteMediaDao
    }

    @After
    fun tearDown() {
        // Close the database after tests are finished
        db.close()
    }

    @Test
    fun upsertFavoriteMediaItemShouldInsertDataCorrectly() = runBlocking {
        val favoriteMedia = FavoriteMediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath.jpg",
            genreIds = "Action,Adventure",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "An exciting action movie",
            popularity = 85.4,
            posterPath = "/posterPath.jpg",
            releaseDate = "2024-12-01",
            title = "Movie Title",
            voteAverage = 7.5,
            voteCount = 1200,
            category = "Action",
            runTime = 120,
            tagLine = "Action Packed",
            videosIds = "12345,67890",
            similarMediaIds = "11111,22222"
        )

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia)

        // Verify the item is inserted correctly
        val retrievedItem = favoriteMediaDao.getFavoriteMediaItemById(1)
        assertNotNull(retrievedItem)
        assertEquals(favoriteMedia.mediaId, retrievedItem?.mediaId)
        assertEquals(favoriteMedia.title, retrievedItem?.title)
    }

    @Test
    fun doesFavoriteMediaExistShouldReturnTrueWhenMediaExists() = runBlocking {
        val favoriteMedia = FavoriteMediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath.jpg",
            genreIds = "Action,Adventure",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "An exciting action movie",
            popularity = 85.4,
            posterPath = "/posterPath.jpg",
            releaseDate = "2024-12-01",
            title = "Movie Title",
            voteAverage = 7.5,
            voteCount = 1200,
            category = "Action",
            runTime = 120,
            tagLine = "Action Packed",
            videosIds = "12345,67890",
            similarMediaIds = "11111,22222"
        )

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia)

        // Verify the item exists in the DB
        val exists = favoriteMediaDao.doesFavoriteMediaExist(1)
        assertTrue(exists)
    }

    @Test
    fun doesFavoriteMediaExistShouldReturnFalseWhenMediaDoesNotExist() = runBlocking {
        // Verify no item exists in the DB initially
        val exists = favoriteMediaDao.doesFavoriteMediaExist(2)
        assertFalse(exists)
    }

    @Test
    fun getLikedMediaListShouldReturnCorrectlikedItems() = runBlocking {
        val favoriteMedia1 = FavoriteMediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath1.jpg",
            genreIds = "Action,Comedy",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title 1",
            overview = "A funny action movie",
            popularity = 90.2,
            posterPath = "/posterPath1.jpg",
            releaseDate = "2024-11-10",
            title = "Liked Movie 1",
            voteAverage = 8.0,
            voteCount = 500,
            category = "Comedy",
            runTime = 100,
            tagLine = "Funny Action",
            videosIds = "11111,22222",
            similarMediaIds = "33333,44444"
        )
        val favoriteMedia2 = FavoriteMediaEntity(
            mediaId = 2,
            isLiked = false,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath2.jpg",
            genreIds = "Drama",
            mediaType = "movie",
            originCountry = "UK",
            originalLanguage = "en",
            originalTitle = "Original Title 2",
            overview = "A sad drama movie",
            popularity = 75.6,
            posterPath = "/posterPath2.jpg",
            releaseDate = "2023-10-05",
            title = "Unliked Movie",
            voteAverage = 6.5,
            voteCount = 600,
            category = "Drama",
            runTime = 130,
            tagLine = "Sad Drama",
            videosIds = "55555,66666",
            similarMediaIds = "77777,88888"
        )

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia1)
        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia2)

        // Verify only liked items are returned
        val likedItems = favoriteMediaDao.getLikedMediaList()
        assertEquals(1, likedItems.size)
        assertTrue(likedItems[0].isLiked)
    }

    @Test
    fun getAllFavoriteMediaItemShouldReturnAllItems() = runBlocking {
        val favoriteMedia1 = FavoriteMediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath1.jpg",
            genreIds = "Action,Adventure",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title 1",
            overview = "Action-packed movie",
            popularity = 90.2,
            posterPath = "/posterPath1.jpg",
            releaseDate = "2024-11-10",
            title = "Movie 1",
            voteAverage = 7.5,
            voteCount = 2000,
            category = "Action",
            runTime = 120,
            tagLine = "Exciting Action",
            videosIds = "99999",
            similarMediaIds = "55555"
        )
        val favoriteMedia2 = FavoriteMediaEntity(
            mediaId = 2,
            isLiked = false,
            isBookmarked = true,
            adult = false,
            backdropPath = "/backdropPath2.jpg",
            genreIds = "Comedy",
            mediaType = "movie",
            originCountry = "UK",
            originalLanguage = "en",
            originalTitle = "Original Title 2",
            overview = "Comedy movie",
            popularity = 85.0,
            posterPath = "/posterPath2.jpg",
            releaseDate = "2024-09-21",
            title = "Movie 2",
            voteAverage = 8.0,
            voteCount = 800,
            category = "Comedy",
            runTime = 100,
            tagLine = "Funny movie",
            videosIds = "11111",
            similarMediaIds = "66666"
        )

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia1)
        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia2)

        val allItems = favoriteMediaDao.getAllFavoriteMediaItem()
        assertEquals(2, allItems.size)
    }

    @Test
    fun deleteFavoriteMediaItemShouldRemoveTheItemCorrectly() = runBlocking {
        val favoriteMedia = FavoriteMediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath1.jpg",
            genreIds = "Action,Adventure",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title 1",
            overview = "An exciting action movie",
            popularity = 85.4,
            posterPath = "/posterPath1.jpg",
            releaseDate = "2024-12-01",
            title = "Movie Title",
            voteAverage = 7.5,
            voteCount = 1200,
            category = "Action",
            runTime = 120,
            tagLine = "Action Packed",
            videosIds = "12345,67890",
            similarMediaIds = "11111,22222"
        )

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia)
        favoriteMediaDao.deleteFavoriteMediaItem(favoriteMedia)

        // Verify the item was deleted
        val retrievedItem = favoriteMediaDao.getFavoriteMediaItemById(1)
        assertNull(retrievedItem)
    }

    @Test
    fun deleteAllFavoriteMediaItemsShouldRemoveAllItems() = runBlocking {
        val favoriteMedia1 = FavoriteMediaEntity(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdropPath1.jpg",
            genreIds = "Action",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalTitle = "Original Title 1",
            overview = "An exciting action movie",
            popularity = 85.4,
            posterPath = "/posterPath1.jpg",
            releaseDate = "2024-12-01",
            title = "Movie Title 1",
            voteAverage = 7.5,
            voteCount = 1200,
            category = "Action",
            runTime = 120,
            tagLine = "Action Packed",
            videosIds = "11111",
            similarMediaIds = "22222"
        )
        val favoriteMedia2 = FavoriteMediaEntity(
            mediaId = 2,
            isLiked = false,
            isBookmarked = true,
            adult = false,
            backdropPath = "/backdropPath2.jpg",
            genreIds = "Comedy",
            mediaType = "movie",
            originCountry = "UK",
            originalLanguage = "en",
            originalTitle = "Original Title 2",
            overview = "A funny movie",
            popularity = 90.2,
            posterPath = "/posterPath2.jpg",
            releaseDate = "2024-11-01",
            title = "Movie Title 2",
            voteAverage = 8.0,
            voteCount = 800,
            category = "Comedy",
            runTime = 100,
            tagLine = "Funny movie",
            videosIds = "33333",
            similarMediaIds = "44444"
        )

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia1)
        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMedia2)

        favoriteMediaDao.deleteAllFavoriteMediaItems()

        // Verify all items were deleted
        val allItems = favoriteMediaDao.getAllFavoriteMediaItem()
        assertTrue(allItems.isEmpty())
    }
}
