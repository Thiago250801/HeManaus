package com.example.hemanaus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.hemanaus.core.model.Booking
import com.example.hemanaus.core.model.User
import com.example.hemanaus.ui.screens.AuthScreen
import com.example.hemanaus.ui.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
                HeManausApp()
        }
    }
}

@Composable
fun HeManausApp() {
    val navController = rememberNavController()
    var currentUser by remember { mutableStateOf<User?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val startRoute = if (currentUser == null) "home" else "auth"
        NavHost(
            navController = navController,
            startDestination = startRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            // HomeScreen
            composable("home") {
                HomeScreen(
                    onStartDonation = {
                        // Navega para a tela de autenticação
                        navController.navigate("auth")
                    }
                )
            }

            // AuthScreen
            composable("auth") {
                AuthScreen(
                    onBackToHome = { navController.popBackStack() }, // Voltar para Home
                    onAuthSucess = { user ->
                        currentUser = user
                        navController.popBackStack() // Voltar para Home após login
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeManausAppPreview() {
    HeManausApp()
}



