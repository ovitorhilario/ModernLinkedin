package com.vitorhilarioapps.modernlinkedin.components.app

import android.view.WindowInsetsAnimation
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vitorhilarioapps.modernlinkedin.Screen
import com.vitorhilarioapps.modernlinkedin.ui.theme.onBackground
import com.vitorhilarioapps.modernlinkedin.ui.theme.primaryTextColor
import com.vitorhilarioapps.modernlinkedin.ui.theme.secondaryBackground

@Composable
fun BottomNavigation(
    navController: NavHostController,
    routes: List<Screen>,
    currentDestination: NavDestination?
) {
    BottomAppBar(
        modifier = Modifier
            .height(56.dp)
            .drawBehind {
                drawLine(
                    color = secondaryBackground,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2.dp.toPx()
                )
            },
        containerColor = onBackground,
        contentColor = primaryTextColor
    ) {

        routes.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = { Icon(painterResource(screen.iconId), contentDescription = null) },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primaryTextColor,
                    selectedTextColor = primaryTextColor,
                    indicatorColor = secondaryBackground,
                    unselectedIconColor = primaryTextColor,
                    unselectedTextColor =  primaryTextColor
                )
            )
        }
    }
}