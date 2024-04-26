package com.planetsistemas.login

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType

// dependence: androidx.navigation:navigation-compose:2.5.0-alpha01
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.MainPage.route,
        modifier = Modifier.padding(6.dp)
    ) {
        composable(route = Screens.MainPage.route) {
            MainPage(navController)
        }
        composable(
            route = Screens.SecondPage.route + "/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) {
            SecondPage(navController, it.arguments?.getString("email"))
        }
    }
}