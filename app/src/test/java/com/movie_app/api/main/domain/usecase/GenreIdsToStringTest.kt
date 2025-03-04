import com.movie_app.api.main.domain.usecase.GenreIdsToString
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreIdsToStringTest {

    @Test
    fun `test genreIdsToString with valid ids`() {
        val genreIds = listOf("27", "53")
        val expected = "Horror - Thriller" // Match with the actual genre names
        val result = GenreIdsToString.genreIdsToString(genreIds)
        assertEquals(expected, result)
    }

    @Test
    fun `test genreIdsToString with invalid ids`() {
        val genreIds = listOf("999", "1000")
        val expected = "null - null"// Assuming your genres list doesn't contain these ids
        val result = GenreIdsToString.genreIdsToString(genreIds)
        assertEquals(expected, result)
    }

    @Test
    fun `test genreIdsToString with empty list`() {
        val genreIds = emptyList<String>()
        val expected = ""
        val result = GenreIdsToString.genreIdsToString(genreIds)
        assertEquals(expected, result)
    }
}
