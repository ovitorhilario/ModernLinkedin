package com.vitorhilarioapps.modernlinkedin.destinations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitorhilarioapps.modernlinkedin.components.feed.Post
import com.vitorhilarioapps.modernlinkedin.utils.fakedata.FakeData

@Composable
fun Feed(
    innerPaddings: PaddingValues,
    onShowBottomSheet: () -> Unit,
    onRepost: () -> Unit,
    onScrollStateChange: (Boolean) -> Unit,
    onOpenComment: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    onScrollStateChange(scrollState.isScrollingUp().value)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(innerPaddings.calculateTopPadding()))
        }

        items(FakeData.posts, key = { it.id }) {
            Post(
                postData = it,
                onShowBottomSheet = { onShowBottomSheet () },
                onRepost = { onRepost() },
                onOpenComment = { postId -> onOpenComment(postId) },
                overview = true
            )
        }

        item {
            Spacer(modifier = Modifier.height(innerPaddings.calculateBottomPadding()))
        }
    }
}

@Composable
fun LazyListState.isScrollingUp(): State<Boolean> {
    return produceState(initialValue = true) {
        var lastIndex = 0
        var lastScroll = Int.MAX_VALUE
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScroll) ->
            if (currentIndex != lastIndex || currentScroll != lastScroll) {
                value = currentIndex < lastIndex ||
                        (currentIndex == lastIndex && currentScroll < lastScroll)
                lastIndex = currentIndex
                lastScroll = currentScroll
            }
        }
    }
}