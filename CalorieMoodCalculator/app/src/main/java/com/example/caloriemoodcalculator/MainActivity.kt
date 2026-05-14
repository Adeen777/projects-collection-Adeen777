package com.example.caloriemoodcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriemoodcalculator.ui.theme.CalorieMoodCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CalorieApp()
        }
    }
}

@Composable
fun CalorieApp() {

    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf("Maintain") }

    var showDialog by remember { mutableStateOf(false) }

    var moodColor by remember { mutableStateOf(Color.Gray) }

    val goals = listOf("Lose Fat", "Maintain", "Gain Muscle")

    val animatedAlpha by animateFloatAsState(
        targetValue = if (result.isNotEmpty()) 1f else 0f,
        label = ""
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (result.isNotEmpty()) 1f else 0.8f,
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF1E1E2E), Color(0xFF121212))
                )
            )
            .padding(20.dp)
    ) {

        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Calorie Mood",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = { showDialog = true }) {
                    Text("ℹ️", fontSize = 20.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text("Height (cm)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Box {
                Button(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(selectedGoal)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    goals.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                selectedGoal = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    if (weight.isNotEmpty() && height.isNotEmpty() && age.isNotEmpty()) {

                        val w = weight.toInt()
                        val h = height.toInt()
                        val a = age.toInt()

                        var calories = (10 * w) + (6 * h) - (5 * a) + 5

                        val mood = when (selectedGoal) {
                            "Lose Fat" -> {
                                calories -= 400
                                moodColor = Color(0xFF42A5F5)
                                "Focused & Disciplined"
                            }
                            "Gain Muscle" -> {
                                calories += 400
                                moodColor = Color(0xFFEF5350)
                                "Energetic & Powerful"
                            }
                            else -> {
                                moodColor = Color(0xFF66BB6A)
                                "Balanced & Stable"
                            }
                        }

                        result = "Calories: $calories\n$mood"
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Calculate", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (result.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = moodColor),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = animatedAlpha
                            scaleX = animatedScale
                            scaleY = animatedScale
                        }
                ) {
                    Text(
                        result,
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("How This Works") },
                text = {
                    Text(
                        "This app transforms your calorie needs into a lifestyle identity.\n\n" +
                                "Fat loss → Discipline\n" +
                                "Muscle gain → Energy\n" +
                                "Maintain → Balance\n\n" +
                                "It’s not just numbers — it’s your mindset."
                    )
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Got it")
                    }
                }
            )
        }
    }
}