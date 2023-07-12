package com.vitorhilarioapps.modernlinkedin.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vitorhilarioapps.modernlinkedin.components.feed.Post
import com.vitorhilarioapps.modernlinkedin.ui.theme.background
import com.vitorhilarioapps.modernlinkedin.utils.fakedata.FakeData

@Composable
fun FullPost(
    postId: Int,
    onShowBottomSheet: () -> Unit,
    onRepost: () -> Unit,
) {
    val postData = FakeData.posts.first { it.id == postId }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(background)
            .verticalScroll(rememberScrollState())
    ){
        Post(
            postData = postData,
            onShowBottomSheet = { onShowBottomSheet () },
            onRepost = { onRepost() },
            onOpenComment = { /* This Screen is the full post */ },
            overview = false
        )
    }
}