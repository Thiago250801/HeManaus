package com.example.hemanaus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hemanaus.core.model.User
import com.example.hemanaus.ui.screens.AuthScreen
import com.example.hemanaus.ui.screens.HomeScreen
import com.google.firebase.auth.FirebaseAuth

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
    val auth = FirebaseAuth.getInstance()

    // Observe Firebase auth state changes
    LaunchedEffect(auth) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            currentUser = if (firebaseUser != null) {
                User(
                    id = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    avatar = firebaseUser.photoUrl?.toString()
                )
            } else {
                null
            }
        }
        auth.addAuthStateListener(authStateListener)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val startRoute = if (auth.currentUser == null) "auth" else "home"
        NavHost(
            navController = navController,
            startDestination = startRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable("home") {

            }

            composable("auth") {
                AuthScreen(
                    onBackToHome = { navController.navigate("home") { popUpTo("auth") { inclusive = true } } },
                    onAuthSucess = { user ->
                        currentUser = user
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
