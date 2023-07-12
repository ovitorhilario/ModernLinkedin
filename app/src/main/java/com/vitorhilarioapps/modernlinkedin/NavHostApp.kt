package com.vitorhilarioapps.modernlinkedin

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vitorhilarioapps.modernlinkedin.destinations.Feed
import com.vitorhilarioapps.modernlinkedin.destinations.FullPost
import com.vitorhilarioapps.modernlinkedin.destinations.Jobs
import com.vitorhilarioapps.modernlinkedin.destinations.Messages
import com.vitorhilarioapps.modernlinkedin.destinations.Network
import com.vitorhilarioapps.modernlinkedin.destinations.NewPost
import com.vitorhilarioapps.modernlinkedin.destinations.Notifications

@Composable
fun NavHostApp(
    modifier: Modifier,
    navController: NavHostController,
    innerPaddings: PaddingValues,
    startDestination: String = Screen.Feed.route,
    onShowBottomSheet: () -> Unit,
    onRepost: () -> Unit,
    onScrollStateChange: (Boolean) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        composable(Screen.Feed.route) {
            Feed(
                innerPaddings = innerPaddings,
                onShowBottomSheet = { onShowBottomSheet() },
                onRepost =  { onRepost() },
                onScrollStateChange = { onScrollStateChange(it) },
                onOpenComment = { postId ->
                    navController.navigate(Screen.Post.route + "/$postId")
                }
            )
        }
        composable(Screen.Messages.route) {
            Messages()
        }
        composable(Screen.Jobs.route) {
            Jobs()
        }
        composable(Screen.Network.route) {
            Network()
        }
        composable(Screen.Notifications.route) {
            Notifications()
        }
        composable(Screen.NewPost.route) {
            NewPost()
        }
        composable(
            route = Screen.Post.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })) { backStackEntry ->

            backStackEntry.arguments?.getInt("postId")?.let {
                FullPost(
                    postId = it,
                    onShowBottomSheet = { onShowBottomSheet() },
                    onRepost =  { onRepost() },
                )
            } ?: navController.popBackStack()
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int, @DrawableRes val iconId: Int) {
    object Feed : Screen("feed", R.string.feed, R.drawable.ic_home)
    object Jobs : Screen("jobs", R.string.jobs, R.drawable.ic_jobs)
    object Network : Screen("network", R.string.network, R.drawable.ic_network)
    object Notifications : Screen("notifications", R.string.notifications, R.drawable.ic_notifications)
    object NewPost : Screen("new-post", R.string.new_post, R.drawable.ic_add)
    object Messages : Screen("messages", R.string.messages, R.drawable.ic_comment)
    object Post: Screen("post", R.string.post, R.drawable.ic_comment)
}
