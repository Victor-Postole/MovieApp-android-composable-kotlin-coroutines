package com.movie_app.api.main.presentation


sealed class MainUiEvents {

    data class Refresh(val route: String) : MainUiEvents()
    data class Paginate(val route: String) : MainUiEvents()

}