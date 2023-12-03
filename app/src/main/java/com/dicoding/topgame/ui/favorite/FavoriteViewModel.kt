package com.dicoding.topgame.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.topgame.data.Repository
import com.dicoding.topgame.model.Game
import com.dicoding.topgame.ui.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<State<List<Game>>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<List<Game>>>
        get() = _uiState

    fun getFavoriteGame() = viewModelScope.launch{
        repository.getFavoriteGame()
            .catch { e ->
                _uiState.value = State.Error(e.message.toString()) }
            .collect {
                _uiState.value = State.Success(it)
            }
    }

    fun updateGame(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateGame(id, newState)
            getFavoriteGame()
    }
}