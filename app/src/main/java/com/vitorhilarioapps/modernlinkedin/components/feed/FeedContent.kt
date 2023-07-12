package com.vitorhilarioapps.modernlinkedin.components.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vitorhilarioapps.modernlinkedin.NavHostApp
import com.vitorhilarioapps.modernlinkedin.R
import com.vitorhilarioapps.modernlinkedin.ui.theme.background
import kotlinx.coroutines.launch

@Composable
fun FeedContent(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    toggleBottomSheet: () -> Unit,
    onScrollStateChange: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    var onRepostAction by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        NavHostApp(
            modifier = Modifier
                .background(background)
                .fillMaxSize(),
            navController = navController,
            innerPaddings = innerPaddings,
            onShowBottomSheet = {
                scope.launch { toggleBottomSheet() }
            },
            onRepost = {
                scope.launch { onRepostAction = !onRepostAction }
            },
            onScrollStateChange = { onScrollStateChange(it) }
        )

        val compositionRepost by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.confetti))
        val progressRepost by animateFloatAsState(
            targetValue = if (onRepostAction) 1f else 0f,
            animationSpec = if (onRepostAction) tween(4000) else tween(0),
            finishedListener = { if (it == 1f) onRepostAction = !onRepostAction },
            label = ""
        )

        if (onRepostAction) {
            LottieAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
                composition = compositionRepost,
                progress = progressRepost
            )
        }
    }
}
