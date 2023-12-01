package com.dicoding.topgame.model

data class Game(
    val id: Int,
    val name: String,
    val genre: String,
    val image: Int,
    val description: String,
    val rating: Double,
    val active: String,
    var isFavorite: Boolean = false
)
