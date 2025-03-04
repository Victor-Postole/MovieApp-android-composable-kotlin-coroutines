package com.movie_app.api.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movie_app.api.favorites.presentation.favorites.FavoritesScreen
import com.movie_app.api.favorites.presentation.favorites_list.FavoritesListScreen
import com.movie_app.api.util.Screen


@Composable
fun CoreFavoritesScreen(
    mainNavController: NavController
) {

    val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
    val favoritesState by favoritesViewModel.favoritesState.collectAsState()

    val favoritesNavController = rememberNavController()

    NavHost(
        navController = favoritesNavController,
        startDestination = Screen.CoreFavorites.route
    ) {
        composable(Screen.CoreFavorites.route) {
            FavoritesScreen(
                mainNavController = mainNavController,
                favoritesNavController = favoritesNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }

        composable(Screen.LikedList.route) {
            FavoritesListScreen(
                route = Screen.LikedList.route,
                mainNavController = mainNavController,
                favoritesNavController = favoritesNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }

        composable(Screen.Bookmarked.route) {
            FavoritesListScreen(
                route = Screen.Bookmarked.route,
                mainNavController = mainNavController,
                favoritesNavController = favoritesNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }
    }

}





















