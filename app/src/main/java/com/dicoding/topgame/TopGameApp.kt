package com.dicoding.topgame

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.topgame.nav.ItemNav
import com.dicoding.topgame.nav.Main
import com.dicoding.topgame.ui.detail.DetailScreen
import com.dicoding.topgame.ui.favorite.FavoriteScreen
import com.dicoding.topgame.ui.home.HomeScreen
import com.dicoding.topgame.ui.profil.Profil

@Composable
fun TopGameApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar =  {
            if (currentRoute != Main.DetailGame.route) {
                BottomBar(navController)
            }
        }, modifier = modifier) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Main.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Main.Home.route) {
                HomeScreen(
                    navigateToDetail = { GameId ->
                        navController.navigate(Main.DetailGame.createRoute(GameId))
                    }
                )
            }
            composable(Main.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { GameId ->
                        navController.navigate(Main.DetailGame.createRoute(GameId))
                    }
                )
            }
            composable(Main.Profile.route) {
                Profil()
            }
            composable(
                route = Main.DetailGame.route,
                arguments = listOf(
                    navArgument("GameId") {
                        type = NavType.IntType
                    }
                )
            ) {
                val id = it.arguments?.getInt("GameId") ?: -1
                DetailScreen(GameId = id, navigateBack = {
                    navController.navigateUp()
                }
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            ItemNav(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Main.Home
            ),
            ItemNav(
                title = stringResource(R.string.fav),
                icon = Icons.Rounded.FavoriteBorder,
                screen = Main.Favorite
            ),
            ItemNav(
                title = stringResource(R.string.profile),
                icon = Icons.Default.AccountCircle,
                screen = Main.Profile
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}