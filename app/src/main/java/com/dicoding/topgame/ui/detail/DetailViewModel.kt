package com.dicoding.topgame.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.topgame.data.Repository
import com.dicoding.topgame.model.Game
import com.dicoding.topgame.ui.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<State<Game>> =
        MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<Game>>
        get() = _uiState

    fun getGameById(id: Int) = viewModelScope.launch {
        _uiState.value = State.Loading
        _uiState.value = State.Success(repository.getGameById(id))
    }

    fun updateGame(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateGame(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getGameById(id)
            }
    }
}