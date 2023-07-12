package com.vitorhilarioapps.modernlinkedin.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vitorhilarioapps.modernlinkedin.ui.theme.primaryTextColor
import com.vitorhilarioapps.modernlinkedin.ui.theme.secondaryBackground

val sharedIcons = listOf(
    "https://img.icons8.com/?size=512&id=16713&format=png",
    "https://img.icons8.com/?size=512&id=32323&format=png",
    "https://img.icons8.com/?size=512&id=63676&format=png",
    "https://img.icons8.com/?size=512&id=13963&format=png",
    "https://img.icons8.com/?size=512&id=13930&format=png",
    "https://img.icons8.com/?size=512&id=63306&format=png"
)

@Composable
fun SheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp)
                .width(48.dp)
                .height(4.dp)
                .background(primaryTextColor, RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
        )

        LazyRow (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.width(16.dp))
            }
            items(sharedIcons) { model ->
                ShareIcon(model)
            }
            item {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun ShareIcon(model: String) {
    Box (
        modifier = Modifier
            .size(48.dp)
            .background(secondaryBackground, CircleShape),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            model = model,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}