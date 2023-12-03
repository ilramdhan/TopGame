package com.dicoding.topgame.nav

sealed class Main(val route: String) {
    object Home : Main("home")
    object Favorite : Main("favorite")
    object Profile : Main("profile")
    object DetailGame : Main("home/{GameId}") {
        fun createRoute(GameId: Int) = "home/$GameId"
    }
}
