package com.example.gymroutinetracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.example.gymroutinetracker.screens.HomeScreen
import com.example.gymroutinetracker.screens.ProgressScreen
import com.example.gymroutinetracker.screens.WorkoutScreen

@Composable
fun GymRoutineApp() {

    val navController = rememberNavController()

    val items = listOf(
        "home",
        "workout",
        "progress"
    )

    Scaffold(

        bottomBar = {

            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route

            NavigationBar {

                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(0)
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = currentRoute == "workout",
                    onClick = {
                        navController.navigate("workout") {
                            popUpTo(0)
                        }
                    },
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = null) },
                    label = { Text("Workout") }
                )

                NavigationBarItem(
                    selected = currentRoute == "progress",
                    onClick = {
                        navController.navigate("progress") {
                            popUpTo(0)
                        }
                    },
                    icon = { Icon(Icons.Default.ShowChart, contentDescription = null) },
                    label = { Text("Progress") }
                )
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {

            composable("home") {
                HomeScreen()
            }

            composable("workout") {
                WorkoutScreen()
            }

            composable("progress") {
                ProgressScreen()
            }
        }
    }
}

@Composable
fun ProgressScreen() {
    TODO("Not yet implemented")
}