package com.dicoding.topgame.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.topgame.R
import com.dicoding.topgame.di.Injection
import com.dicoding.topgame.model.Game
import com.dicoding.topgame.ui.component.List
import com.dicoding.topgame.ui.component.SearchButton
import com.dicoding.topgame.ui.state.State
import com.dicoding.topgame.ui.state.ViewModelFactory


@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel( factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Int) -> Unit, ) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = State.Loading).value.let { uiState ->
        when (uiState) {
            is State.Loading -> {
                viewModel.search(query)
            }
            is State.Success -> {
                Content(query = query, onQueryChange = viewModel::search, listGame = uiState.data, onFavoriteIconClicked = { id, newState ->
                        viewModel.updateGame(id, newState)
                    }, navigateToDetail = navigateToDetail
                )
            }
            is State.Error -> {}
        }
    }
}

@Composable
fun Content(query: String, onQueryChange: (String) -> Unit, listGame: List<Game>, onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit, modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column {
        SearchButton(query = query, onQueryChange = onQueryChange)
        if (listGame.isNotEmpty()) {
            ListGame(listGame = listGame, onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            List(warning = stringResource(R.string.empty_list), modifier = Modifier.testTag("empty_list"))
        }
    }
}

@Composable
fun ListGame(
    listGame: List<Game>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,
    ) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_column")
    ){
        items(listGame, key = {it.id}){ game ->
            com.dicoding.topgame.ui.component.Item(
                id = game.id,
                name = game.name,
                genre = game.genre,
                image = game.image,
                rating = game.rating,
                isFavorite = game.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier.testTag("item")
                    .clickable { navigateToDetail(game.id) }
            )
        }
    }
}

