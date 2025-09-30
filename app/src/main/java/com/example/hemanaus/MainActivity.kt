package com.example.hemanaus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hemanaus.core.model.User
import com.example.hemanaus.ui.screens.AuthScreen
import com.example.hemanaus.ui.screens.HomeScreen
import com.example.hemanaus.ui.screens.RequirementsScreen
import com.example.hemanaus.ui.screens.UserInfoScreen
import com.google.firebase.auth.FirebaseAuth
import com.hemoam.app.ui.screens.BookingScreen


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

    // Observa mudanças de autenticação do Firebase
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


        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.fillMaxSize()
        ) {
            // Home / Landing Page
            composable("home") {
                HomeScreen(onStartDonation = { navController.navigate("auth") })
            }

            // Auth Screen
            composable("auth") {
                AuthScreen(
                    onBackToHome = {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onAuthSucess = { user ->
                        currentUser = user
                        navController.navigate("userInfo") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }

            // User Info Screen
            composable("userInfo") {
                currentUser?.let { user ->
                    UserInfoScreen(
                        user = user,
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            currentUser = null
                            navController.navigate("home") {
                                popUpTo("userInfo") { inclusive = true }
                            }
                        },
                        onContinue = { fullName, phone ->
                            // Atualiza o nome do usuário no Firebase
                            val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName)
                                .build()
                            auth.currentUser?.updateProfile(profileUpdates)

                            // Navega para a Requirements após atualizar as informações
                            navController.navigate("requirements") {
                                popUpTo("userInfo") { inclusive = true }
                            }
                        }
                    )
                }
            }

            composable("requirements") {
                RequirementsScreen(
                    onBack = {
                        navController.navigate("userInfo") {
                            popUpTo("requirements") { inclusive = true }
                        }
                    },
                    onAceppted = {
                        navController.navigate("booking") {
                            popUpTo("requirements") { inclusive = true }
                        }
                    }
                )
            }

            composable("booking") {
                BookingScreen(
                    onBack = {
                        navController.navigate("requirements"){
                            popUpTo("booking") { inclusive = true }
                        }
                    },
                    onComplete = {}
                )
            }
        }
    }
}


