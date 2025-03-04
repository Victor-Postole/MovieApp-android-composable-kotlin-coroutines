package com.movie_app.api.util


sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Trending : Screen("trending")
    object Tv : Screen("tv")
    object Movies : Screen("movies")

    object SimilarMediaId : Screen("similar//{videoId}")

    object CoreDetails : Screen("core_details")
    object Details : Screen("details")
    object WatchVideo : Screen("watch_video/{videoId}")  // Expect videoId as an argument
    object Similar : Screen("similar")
    object Search : Screen("search")
    object CoreFavorites : Screen("core_favorites")
    object LikedList : Screen("liked_list")
    object Bookmarked : Screen("bookmark")


    object CoreCategories : Screen("core_categories")
    object Categories : Screen("categories")
    object CategoriesList : Screen("categories_list")

}






















