package com.dicoding.topgame.data

import com.dicoding.topgame.model.Game
import com.dicoding.topgame.model.GameData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class Repository {
    private val listGame = mutableListOf<Game>()

    init {
        if (listGame.isEmpty()) {
            GameData.dummyGame.forEach {
                listGame.add(it)
            }
        }
    }

    fun getGameById(GameId: Int): Game {
        return listGame.first {
            it.id == GameId
        }
    }

    fun getFavoriteGame(): Flow<List<Game>> {
        return flowOf(listGame.filter { it.isFavorite })
    }

    fun searchGame(query: String) = flow {
        val data = listGame.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateGame(GameId: Int, newState: Boolean): Flow<Boolean> {
        val index = listGame.indexOfFirst { it.id == GameId }
        val result = if (index >= 0) {
            val game = listGame[index]
            listGame[index] = game.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}