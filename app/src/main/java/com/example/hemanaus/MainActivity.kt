package com.example.hemanaus

import BookingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hemanaus.core.model.User
import com.example.hemanaus.core.viewmodel.BookingViewModel
import com.example.hemanaus.ui.screens.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

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
    val bookingViewModel: BookingViewModel = viewModel()
    val auth = FirebaseAuth.getInstance()
    var currentUser by remember { mutableStateOf<User?>(null) }

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
                ).also { bookingViewModel.setUser(it) }
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
            // Home Screen
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
                        bookingViewModel.setUser(user)
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
                        bookingViewModel = bookingViewModel,
                        user = user,
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            bookingViewModel.clearBooking()
                            currentUser = null
                            navController.navigate("home") {
                                popUpTo("userInfo") { inclusive = true }
                            }
                        },
                        onContinue = { fullName, phone ->
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName)
                                .build()
                            auth.currentUser?.updateProfile(profileUpdates)

                            bookingViewModel.setUser(user.copy(name = fullName, phone = phone))
                            navController.navigate("booking") {
                                popUpTo("userInfo") { inclusive = true }
                            }
                        },
                    )
                }
            }

            // Booking Screen
            composable("booking") {
                BookingScreen(
                    bookingViewModel = bookingViewModel,
                    onBack = { navController.navigate("userInfo") },
                    onComplete = { navController.navigate("preparation") }
                )
            }

            // Preparation Screen
            composable("preparation") {
                PreparationScreen(
                    bookingViewModel = bookingViewModel,
                    onBack = { navController.navigate("booking") },
                    onCheckIn = { navController.navigate("checkin") }
                )
            }

            // CheckIn Screen
            composable("checkin") {
                CheckInScreen(
                    bookingViewModel = bookingViewModel,
                    onBack = { navController.navigate("preparation") },
                    onComplete = { navController.navigate("postDonation") }
                )
            }

            // PostDonation Screen
            composable("postDonation") {
                PostDonationScreen(
                    bookingViewModel = bookingViewModel,
                    onBack = { navController.navigate("home") },
                    onNewDonation = {
                        bookingViewModel.clearBooking()
                        navController.navigate("booking")
                    }
                )
            }
        }
    }
}
