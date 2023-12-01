package com.dicoding.topgame.data

import com.dicoding.topgame.model.Game
import com.dicoding.topgame.model.GameData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.internal.NopCollector.emit

class Repository {
    private val listGame = mutableListOf<Game>()

    init {
        if (listGame.isEmpty()) {
            GameData.dummyGame.forEach {
                listGame.add(it)
            }
        }
    }

    fun getGameById(gameId: Int): Game {
        return listGame.first {
            it.id == gameId
        }
    }

    fun getFavoriteGame(): List<Game> {
        return flowOf(listGame.filter { it.isFavorite })
    }

    fun searchGame(query: String) = flow {
        val data = listGame.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateGame(gameId: Int, newState: Boolean): Flow<Boolean> {
        val index = listGame.indexOfFirst { it.id == gameId }
        val result = if (index >= 0) {
            val game = listGame[index]
            listGame[index] = game.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }
}