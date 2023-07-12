package com.vitorhilarioapps.modernlinkedin

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vitorhilarioapps.modernlinkedin.components.app.BottomNavigation
import com.vitorhilarioapps.modernlinkedin.components.app.BottomSheetLayout
import com.vitorhilarioapps.modernlinkedin.components.app.DrawerLayout
import com.vitorhilarioapps.modernlinkedin.components.app.TopBar
import com.vitorhilarioapps.modernlinkedin.ui.theme.ModernLinkedinTheme
import com.vitorhilarioapps.modernlinkedin.utils.fakedata.FakeData
import kotlinx.coroutines.launch

@Composable
fun LinkedInApp() {
    ModernLinkedinTheme {
        Surface(Modifier.fillMaxSize()) { AppContent() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val routes = listOf(Screen.Feed, Screen.Network, Screen.NewPost, Screen.Notifications, Screen.Jobs)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isScrolling by remember { mutableStateOf(true) }

    ModalNavigationDrawer (
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            DrawerLayout(
                FakeData.users[0].name,
                FakeData.users[0].avatarModel,
                FakeData.users[0].bannerModel,
                FakeData.users[0].resume,
                routes,
                navController,
                currentDestination,
                onCloseDrawer = { scope.launch { drawerState.close() }}
            )
        },
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    AnimatedVisibility(
                        visible = isVisible(currentDestination?.route, isScrolling),
                        enter = slideInVertically(initialOffsetY = { 0 }, animationSpec = tween(500)),
                        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(500)),
                    ) {
                        TopBar(
                            FakeData.users[0].avatarModel,
                            onOpenMessages = {
                                navController.navigate(Screen.Messages.route)
                            },
                            onOpenDrawer = { scope.launch { drawerState.open() }}
                        )
                    }
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = isVisible(currentDestination?.route, isScrolling),
                        enter = slideInVertically(initialOffsetY = { 0 }, animationSpec = tween(500)),
                        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(500))
                    ) {
                        BottomNavigation(navController, routes, currentDestination)
                    }
                },
            ) { _ ->

                BottomSheetLayout(
                    innerPaddings = PaddingValues(bottom = 72.dp, top = 64.dp),
                    navController,
                    onScrollStateChange = { if (isScrolling != it) isScrolling = it }
                )
            }
        },
    )
}

fun isVisible(
    currentRoute: String?,
    isScrolling: Boolean
): Boolean {
    val fullScreens = listOf(Screen.Messages, Screen.Post)

    return if (fullScreens.any { currentRoute?.startsWith(it.route) == true }) {
        false
    } else {
        isScrolling
    }
}




