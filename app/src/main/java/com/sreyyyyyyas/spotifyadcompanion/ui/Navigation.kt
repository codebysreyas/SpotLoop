@file:OptIn(ExperimentalMaterial3Api::class)

package com.sreyyyyyyas.spotifyadcompanion.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("about") { AboutScreen(navController) }
    }
}
