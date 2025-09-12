package com.example.hemanaus.data

data class User(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val email: String,
    val avatar: String? = null,
    val provider: AuthProvider = AuthProvider.EMAIL
)

enum class AuthProvider(val displayName: String) {
    EMAIL("Email"),
    GOOGLE("Google")
}

data class AuthState(
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)