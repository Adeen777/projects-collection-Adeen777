package com.example.gymroutinetracker.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressScreen() {

    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences(
        "GymWorkouts",
        Context.MODE_PRIVATE
    )

    val workouts = sharedPreferences.getStringSet("workouts", setOf()) ?: setOf()

    val history = sharedPreferences.getStringSet("history", setOf()) ?: setOf()

    val totalWorkouts = workouts.size
    val totalSessions = history.size

    val streak = calculateStreak(history.toList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Fitness Analytics",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Workout Types", color = Color.Gray)
                Text("$totalWorkouts", color = Color(0xFFE53935), fontSize = 32.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Total Sessions Logged", color = Color.Gray)
                Text("$totalSessions", color = Color.White, fontSize = 32.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Current Streak", color = Color.Gray)
                Text("$streak days", color = Color(0xFF4CAF50), fontSize = 32.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recent Activity",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(history.toList().takeLast(10)) { item ->

                Card(
                    colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {

                    Text(
                        text = item,
                        color = Color.LightGray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

fun calculateStreak(history: List<String>): Int {

    if (history.isEmpty()) return 0

    return history.distinct().size
}