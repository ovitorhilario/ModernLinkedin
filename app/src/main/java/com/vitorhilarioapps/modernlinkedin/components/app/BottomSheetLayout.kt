package com.vitorhilarioapps.modernlinkedin.components.app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vitorhilarioapps.modernlinkedin.components.feed.FeedContent
import com.vitorhilarioapps.modernlinkedin.ui.theme.onBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    onScrollStateChange: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetBackgroundColor = onBackground,
        sheetContent = { SheetContent() },
        content = {
            FeedContent(
                innerPaddings = innerPaddings,
                navController = navController,
                toggleBottomSheet = {
                    scope.launch {
                        if (!sheetState.isVisible)
                            sheetState.show()
                        else sheetState.hide()
                    }
                },
                onScrollStateChange = { onScrollStateChange(it) }
            )
        }
    )
}