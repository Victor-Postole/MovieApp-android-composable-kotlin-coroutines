package com.movie_app.api

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SharedPreferencesTest {

    private lateinit var sharedPreferences: android.content.SharedPreferences

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
    }

    @Test
    fun testSharedPreferences() {
        // Arrange
        val editor = sharedPreferences.edit()
        editor.putString("test_key", "test_value")
        editor.apply()

        // Act
        val value = sharedPreferences.getString("test_key", null)

        // Assert
        assertEquals("test_value", value)
    }
}