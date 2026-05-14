package com.example.gymroutinetracker.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkoutScreen() {

    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences(
        "GymWorkouts",
        Context.MODE_PRIVATE
    )

    var workouts by remember {
        mutableStateOf<List<String>>(
            sharedPreferences.getStringSet(
                "workouts",
                setOf(
                    "Chest Day",
                    "Back Workout",
                    "Leg Day"
                )
            )?.toList() ?: listOf("Chest Day", "Back Workout", "Leg Day")
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }
    var inputText by remember { mutableStateOf("") }

    fun save(list: List<String>) {
        val history = sharedPreferences.getStringSet("history", mutableSetOf())?.toMutableList() ?: mutableListOf()

        history.add("Workout added: $inputText")

        sharedPreferences.edit()
            .putStringSet("history", history.toSet())
            .apply()

        sharedPreferences.edit()
            .putStringSet("workouts", list.toSet())
            .apply()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    inputText = ""
                    editIndex = -1
                    showDialog = true
                },
                containerColor = Color(0xFFE53935)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        },
        containerColor = Color(0xFF111111)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                text = "Workout Tracker",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {

                items(workouts.size) { index ->

                    val item = workouts[index]

                    AnimatedVisibility(true) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
                            shape = RoundedCornerShape(16.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    Icons.Default.FitnessCenter,
                                    contentDescription = null,
                                    tint = Color(0xFFE53935)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = item,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(onClick = {
                                    inputText = item
                                    editIndex = index
                                    showDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Yellow)
                                }

                                IconButton(onClick = {
                                    val updated = workouts.toMutableList()
                                    updated.removeAt(index)
                                    workouts = updated
                                    save(updated)
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(if (editIndex == -1) "Add Workout" else "Edit Workout")
            },
            text = {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Workout Name") }
                )
            },
            confirmButton = {
                Button(onClick = {

                    if (inputText.isNotEmpty()) {

                        val updated = workouts.toMutableList()

                        if (editIndex == -1) {
                            updated.add(inputText)
                        } else {
                            updated[editIndex] = inputText
                        }

                        workouts = updated
                        save(updated)

                        showDialog = false
                    }
                }) {
                    Text("Save")
                }
            }
        )
    }
}
