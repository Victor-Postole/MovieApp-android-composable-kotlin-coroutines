package com.movie_app.api.details.presentation

import YouTubePlayerComposable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.movie_app.api.details.presentation.details.DetailsScreen
import com.movie_app.api.details.presentation.details.DetailsUiEvents
import com.movie_app.api.details.presentation.details.DetailsViewModel
import com.movie_app.api.util.Screen


@Composable
fun CoreDetailsScreen(
    mediaId: Int,
    mainNavController: NavController
) {

    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState by detailsViewModel.detailsState.collectAsState()

    LaunchedEffect(true) {
        detailsViewModel.onEvent(
            DetailsUiEvents.StartLoading(mediaId)
        )
    }

    val detailsNavController = rememberNavController()

    NavHost(
        navController = detailsNavController,
        startDestination = Screen.Details.route
    ) {
        composable(Screen.Details.route) {
            DetailsScreen(
                mainNavController = mainNavController,
                detailsNavController = detailsNavController,
                detailsState = detailsState,
                onEvent = detailsViewModel::onEvent
            )
        }

        composable(
            route = "${Screen.WatchVideo.route}/{videoId}",
            arguments = listOf(navArgument("videoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val context = LocalContext.current
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            YouTubePlayerComposable(context = context, videoId = videoId)

        }

        composable(Screen.Similar.route) {
            DetailsScreen(
                mainNavController = mainNavController,
                detailsNavController = detailsNavController,
                detailsState = detailsState,
                onEvent = detailsViewModel::onEvent
            )
        }
    }

}
























