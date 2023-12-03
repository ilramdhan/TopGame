package com.dicoding.topgame.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme.typography
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.topgame.R

@Composable
fun Item(
    id: Int,
    name: String,
    genre: String,
    image: Int,
    rating: Double,
    isFavorite: Boolean = false,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically){
            Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
            ){
                Image(painter = painterResource(image), contentDescription = "image_game", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = name, style = typography.h6)
                Text(text = genre, style = typography.subtitle1)
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.Star, contentDescription = "rating", tint = Color.Yellow, modifier = Modifier.padding(end = 2.dp).size(16.dp))
                    Text(text = rating.toString(), style = typography.body2)
                }
            }
        }
        Icon(
            imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "favorite",
            tint = if (!isFavorite) Color.Gray else Color.Red,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .size(32.dp)
                .testTag("favorite_icon")
                .clickable{onFavoriteIconClicked(id, !isFavorite)}
        )
    }
}

@Preview
@Composable
fun ItemPreview(){
    Item(
        id = 1,
        name = "The Witcher 3",
        genre = "RPG",
        image = R.drawable.wild,
        rating = 4.5,
        isFavorite = true,
        onFavoriteIconClicked = {id, newState -> }
    )
}