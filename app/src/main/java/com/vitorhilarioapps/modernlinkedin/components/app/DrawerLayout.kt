package com.vitorhilarioapps.modernlinkedin.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vitorhilarioapps.modernlinkedin.Screen
import com.vitorhilarioapps.modernlinkedin.ui.theme.Typography
import com.vitorhilarioapps.modernlinkedin.ui.theme.onBackground
import com.vitorhilarioapps.modernlinkedin.ui.theme.primaryTextColor
import com.vitorhilarioapps.modernlinkedin.ui.theme.secondaryBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerLayout(
    userName: String,
    avatarModel: String,
    bannerModel: String,
    resume: String,
    routes: List<Screen>,
    navController: NavHostController,
    currentDestination: NavDestination?,
    onCloseDrawer: () -> Unit,
) {
    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
        drawerContainerColor = onBackground
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.TopCenter),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(bannerModel)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )

                    val pxValueTarget = with(LocalDensity.current) { 120.dp.toPx() }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(listOf(Color.Transparent, onBackground), start = Offset(0f, 0f), end = Offset(0f, pxValueTarget)),
                                RoundedCornerShape(0.dp)
                            )
                    )
                }

                AsyncImage(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomStart)
                    ,
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(avatarModel)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

                Column (
                    modifier = Modifier
                        .padding(start = 96.dp, bottom = 16.dp, end = 50.dp)
                        .align(Alignment.BottomStart),
                ) {
                    Text(
                        text = userName,
                        fontWeight = FontWeight.Bold,
                        fontSize = Typography.titleLarge.fontSize,
                        color = primaryTextColor,
                        maxLines = 1,
                    )

                    Text(
                        text = resume,
                        fontWeight = FontWeight.Normal,
                        fontSize = Typography.labelSmall.fontSize,
                        color = primaryTextColor,
                        maxLines = 1,
                    )
                }

                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 11.dp)
                        .size(28.dp)
                        .align(Alignment.BottomEnd),
                    imageVector = Icons.Rounded.ExitToApp,
                    contentDescription = null,
                    tint = primaryTextColor
                )
            }

            routes.forEach { screen ->

                Spacer(modifier = Modifier.height(16.dp))

                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = { Text(text = stringResource(id = screen.resourceId)) },
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

                        onCloseDrawer()
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = secondaryBackground
                    )
                )
            }

        }
    }
}