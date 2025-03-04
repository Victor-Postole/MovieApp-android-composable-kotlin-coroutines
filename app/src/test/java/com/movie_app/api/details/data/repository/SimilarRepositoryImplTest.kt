import android.app.Application
import com.movie_app.api.details.data.remote.api.DetailsApi
import com.movie_app.api.details.data.repository.SimilarRepositoryImpl
import com.movie_app.api.favorites.domain.repository.FavoritesRepository
import com.movie_app.api.main.data.remote.dto.MediaDto
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.main.domain.repository.MainRepository
import com.movie_app.api.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class SimilarRepositoryImplTest {

    private lateinit var similarRepository: SimilarRepositoryImpl
    private val detailsApi: DetailsApi = mock()
    private val mainRepository: MainRepository = mock()
    private val application: Application = mock()
    private val favoritesRepository: FavoritesRepository = mock()

    @Before
    fun setUp() {
        // Mock application string response
        whenever(application.getString(any())).thenReturn("Couldn't load data")

        similarRepository = SimilarRepositoryImpl(
            detailsApi = detailsApi,
            mainRepository = mainRepository,
            application = application,
            favoritesRepository = favoritesRepository
        )
    }

    // Test when local media is found
    @Test
    fun `test getSimilarMedia when local media is found`() = runBlockingTest {
        // Arrange
        val mediaId = 123
        val media = createSampleMedia(mediaId)
        val localSimilarList = listOf(media.copy(mediaId = 124))

        // Mock repository methods
        whenever(mainRepository.getMediaById(mediaId)).thenReturn(media)
        whenever(mainRepository.getMediaListByIds(any())).thenReturn(localSimilarList)

        // Act
        val flow = similarRepository.getSimilarMedia(isRefresh = false, id = mediaId)

        // Assert
        flow.collect { result ->
            when (result) {
                is Resource.Loading -> assertTrue(true)
                is Resource.Success -> {
                    // Assert that the shuffled list is the same as the local list
                    assertEquals(localSimilarList, result.data)
                }
                else -> {}
            }
        }
    }

    @Test
    fun `test getSimilarMedia when api media is found`() = runBlockingTest {
        // Arrange
        val mediaId = 710295
        val media = createSampleMedia(mediaId)
        val apiSimilar = listOf(media.copy(mediaId = 710295))

        // Mock repository methods
        whenever(mainRepository.getMediaById(mediaId)).thenReturn(media)  // Ensure this returns a non-null Media object
        val foundMedia = apiSimilar.find { it.mediaId == mediaId }

        if (foundMedia != null) {
            whenever(mainRepository.getMediaListByIds(any())).thenReturn(listOf(foundMedia))
        }

        // Act
        val flow = similarRepository.getSimilarMedia(isRefresh = false, id = mediaId)

        // Assert
        flow.collect { result ->
            when (result) {
                is Resource.Loading -> assertTrue(true)
                is Resource.Success -> {
                    // Assert that the shuffled list is the same as the local list
                    assertEquals(apiSimilar, result.data)
                }
                else -> {}
            }
        }
    }

    // Helper function to create sample Media
    private fun createSampleMedia(mediaId: Int): Media {
        return Media(
            mediaId = mediaId,
            isLiked = false,
            isBookmarked = false,
            adult = false,
            backdropPath = "/wwARk7hRIfHfh2n2ubN6N7lvTne.jpg",
            genreIds = listOf("27", "53"),
            mediaType = "movie",
            originCountry = emptyList(),
            originalLanguage = "en",
            originalTitle = "Wolf Man",
            overview = "With his marriage fraying, Blake persuades his wife Charlotte to take a break from the city and visit his remote childhood home in rural Oregon. As they arrive at the farmhouse in the dead of night, they're attacked by an unseen animal and barricade themselves inside the home as the creature prowls the perimeter. But as the night stretches on, Blake begins to behave strangely, transforming into something unrecognizable.",
            popularity = 327.557,
            posterPath = "/tUtuMtC6oaRXr4x2B5Xi6ABdMCv.jpg",
            releaseDate = "2025-01-15",
            title = "Wolf Man",
            voteAverage = 6.8,
            voteCount = 72,
            category = "trending",
            runTime = 0,
            tagLine = "",
            videosIds = emptyList(),
            similarMediaIds = emptyList()
        )
    }
    // Helper function to create Media from DTO
    private fun createMediaFromDto(dto: MediaDto): Media {
        return Media(
            mediaId = dto.id ?: -1,
            isLiked = false,
            isBookmarked = false,
            adult = false,
            backdropPath = dto.backdrop_path ?: "",
            mediaType = dto.media_type ?: "",
            originCountry = dto.origin_country ?: listOf(),
            originalLanguage = dto.original_language ?: "",
            originalTitle = dto.original_title ?: "",
            overview = dto.overview ?: "",
            popularity = dto.popularity ?: 0.0,
            posterPath = dto.poster_path ?: "",
            releaseDate = dto.release_date ?: "",
            title = dto.title ?: "",
            voteAverage = dto.vote_average ?: 0.0,
            voteCount = dto.vote_count ?: 0,
            category = "",
            runTime = 0,
            tagLine = "",
            videosIds = listOf(),
            similarMediaIds = listOf(),
            genreIds = listOf()
        )
    }
}
