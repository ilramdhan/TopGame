package com.dicoding.topgame.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.topgame.R
import com.dicoding.topgame.di.Injection
import com.dicoding.topgame.model.Game
import com.dicoding.topgame.ui.home.ListGame
import com.dicoding.topgame.ui.state.State
import com.dicoding.topgame.ui.state.ViewModelFactory

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: FavoriteViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
){
    viewModel.uiState.collectAsState(initial = State.Loading).value.let { uiState ->
        when (uiState) {
            is State.Loading -> {
                viewModel.getFavoriteGame()
            }
            is State.Success -> {
                Favorite(listGame = uiState.data, navigateToDetail = navigateToDetail, onFavoriteIconClicked = { id, newState ->
                    viewModel.updateGame(id, newState)
                })
            }
            is State.Error -> {}
        }
    }
}

@Composable
fun Favorite(listGame: List<Game>, navigateToDetail: (Int) -> Unit, onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        if (listGame.isNotEmpty()) {
            ListGame(listGame = listGame, onFavoriteIconClicked = onFavoriteIconClicked, contentPaddingTop = 16.dp, navigateToDetail = navigateToDetail)
        }else{
            com.dicoding.topgame.ui.component.List(warning = stringResource(R.string.empty_list))
        }
    }
}