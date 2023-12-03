package com.dicoding.topgame.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.topgame.R
import com.dicoding.topgame.di.Injection
import com.dicoding.topgame.ui.state.State
import com.dicoding.topgame.ui.state.ViewModelFactory

@Composable
fun DetailScreen(
    GameId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
){
    viewModel.uiState.collectAsState(initial = State.Loading).value.let { uiState ->
        when (uiState) {
            is State.Loading -> {
                viewModel.getGameById(GameId)
            }
            is State.Success -> {
                val data = uiState.data
                Detail(
                    id = data.id,
                    name = data.name,
                    genre = data.genre,
                    image = data.image,
                    description = data.description,
                    rating = data.rating,
                    active = data.active,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack
                ) { id, state ->
                    viewModel.updateGame(id, state)
                }
            }is State.Error -> {}
        }
    }
}

@Composable
fun Detail(
    id: Int,
    name: String,
    genre: String,
    @DrawableRes image: Int,
    description: String,
    rating: Double,
    active: String,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)) {
            Image(painter = painterResource(image), contentDescription = name, contentScale = ContentScale.Crop, modifier = Modifier
                .fillMaxWidth()
                .testTag("image"))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.Star, contentDescription = "rating", modifier = Modifier.size(16.dp))
                    Text(text = rating.toString(), modifier=Modifier.padding(start = 2.dp, end = 8.dp))
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.Info,contentDescription = "genre", modifier = Modifier.size(16.dp))
                    Text(text = genre, modifier=Modifier.padding(start = 2.dp, end = 8.dp))
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.CheckCircle,contentDescription = "active", modifier = Modifier.size(16.dp))
                    Text(text = active, overflow = TextOverflow.Visible, modifier=Modifier.padding(start = 4.dp))
                }
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(text = description, fontSize = 16.sp, lineHeight = 28.sp, modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(onClick = navigateBack, modifier = Modifier.padding(start = 16.dp, top = 8.dp).align(Alignment.TopStart).clip(
            CircleShape).size(40.dp).testTag("back_button").background(Color.White)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
        }
        IconButton(onClick = { onFavoriteButtonClicked(id, isFavorite) }, modifier = Modifier.padding(end = 16.dp, top = 8.dp).align(Alignment.TopEnd).clip(
            CircleShape).size(40.dp).testTag("favorite_button").background(Color.White)) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!isFavorite) stringResource(R.string.favorite) else stringResource(R.string.remove),
                tint = if (!isFavorite) Color.Black else Color.Red
            )
        }
    }
}
