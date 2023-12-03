package com.dicoding.topgame.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.topgame.R

@Composable
fun SearchButton(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(value = query, onValueChange = onQueryChange, leadingIcon = {
        Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
    }, singleLine = true, shape = RoundedCornerShape(50), colors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.White,
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    ),  placeholder = {
        Text(stringResource(id = R.string.search))
    }, modifier = modifier.padding(16.dp).fillMaxWidth().heightIn(min = 48.dp).shadow(56.dp))
}

@Preview
@Composable
fun SearchPreview() {
    SearchButton(query = "Sample Query", onQueryChange = {})
}