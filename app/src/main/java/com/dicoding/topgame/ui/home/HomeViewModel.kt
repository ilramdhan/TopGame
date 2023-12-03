package com.dicoding.topgame.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.topgame.data.Repository
import com.dicoding.topgame.model.Game
import com.dicoding.topgame.ui.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<State<List<Game>>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<List<Game>>> get() = _uiState
    private val _query = mutableStateOf("")
    val query: MutableState<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchGame(_query.value)
            .catch { e ->
                _uiState.value = State.Error(e.message.toString()) }
            .collect {
                _uiState.value = State.Success(it)
            }
    }

    fun updateGame(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateGame(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}