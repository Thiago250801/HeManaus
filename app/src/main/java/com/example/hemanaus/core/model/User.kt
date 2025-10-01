package com.example.hemanaus.core.model

data class User(
    val id: String,
    val name: String,
    val phone: String? = null,
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