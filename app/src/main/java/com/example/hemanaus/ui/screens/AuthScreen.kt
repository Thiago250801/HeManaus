package com.example.hemanaus.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hemanaus.R
import com.example.hemanaus.core.model.AuthProvider
import com.example.hemanaus.core.model.User
import com.example.hemanaus.ui.components.HemoamButton
import com.example.hemanaus.ui.components.HemoamCard
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hemoam.app.ui.theme.Red600
import com.hemoam.app.ui.theme.Red700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onBackToHome: () -> Unit,
    onAuthSucess: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    var isLogin by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    /// Quando n estiver em demo passar valor para false
    val useMockGoogleLogin = true
    // --- Google Sign-In setup ---
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(Exception::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val user = User(
                        id = firebaseUser?.uid ?: "",
                        name = firebaseUser?.displayName ?: "",
                        email = firebaseUser?.email ?: "",
                        avatar = firebaseUser?.photoUrl?.toString(),
                        provider = AuthProvider.GOOGLE
                    )
                    onAuthSucess(user)
                } else {
                    errorMessage = authResult.exception?.message ?: "Erro ao autenticar com Google"
                }
                isLoading = false
            }
        } catch (e: Exception) {
            errorMessage = e.message
            isLoading = false
        }
    }

    fun validateForm(): String? {
        return when {
            email.isEmpty() && password.isEmpty() -> "E-mail e Senha são obrigatórios"
            email.isEmpty() -> "Email é obrigatório"
            password.isEmpty() -> "Senha é obrigatória"
            !isLogin && name.isEmpty() -> "Nome é obrigatório"
            !isLogin && password.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
            !isLogin && password != confirmPassword -> "Senhas não coincidem"
            else -> null
        }
    }

    val onBack: () -> Unit = {
        if (!isLogin) {
            isLogin = true
            name = ""
            email = ""
            password = ""
            confirmPassword = ""
            errorMessage = null
        } else {
            onBackToHome()
        }
    }

    // --- Scaffold com TopAppBar ---
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        text = if (isLogin) "Entrar" else "Criar Conta",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Red600
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Login com Google ---
            HemoamCard {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                    Text(text = "Acesso Rápido", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Entre ou crie sua conta de forma rápida e segura",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            if (useMockGoogleLogin) {
                                // Mock de usuário
                                val user = User(
                                    id = "mock_google_id",
                                    name = "Usuário Demo",
                                    email = "demo@hemanaus.com",
                                    avatar = null,
                                    provider = AuthProvider.GOOGLE
                                )
                                onAuthSucess(user)
                            } else {
                                // Código real de login Google
                                isLoading = true
                                val signInIntent: Intent = googleSignInClient.signInIntent
                                launcher.launch(signInIntent)
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google_icon),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isLoading) "Conectando..." else "Continuar com Google",
                            color = Color.Black
                        )
                    }

                    if (errorMessage != null) {
                        Text(text = errorMessage!!, color = Red700, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }

            // --- Separador ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(
                    text = "ou continue com email",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Divider(modifier = Modifier.weight(1f))
            }

            // --- Formulário Email/Senha ---
            HemoamCard {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isLogin) "Login" else "Criar Conta",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (isLogin) "Entre com seu email e senha" else "Preencha os dados para criar sua conta",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (!isLogin) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it; errorMessage = null },
                            label = { Text("Nome Completo") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = errorMessage?.contains("Nome") == true
                        )
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; errorMessage = null },
                        label = { Text("E-mail") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = errorMessage?.contains("Email") == true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        label = { Text("Senha") },
                        leadingIcon = { Icon(Icons.Default.Password, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = errorMessage?.contains("Senha") == true
                    )

                    if (!isLogin) {
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it; errorMessage = null },
                            label = { Text("Confirmar Senha") },
                            leadingIcon = { Icon(Icons.Default.Password, contentDescription = null) },
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                    Icon(
                                        imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (confirmPasswordVisible) "Ocultar senha" else "Mostrar senha"
                                    )
                                }
                            },
                            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = errorMessage?.contains("coincidem") == true || errorMessage?.contains("caracteres") == true
                        )
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Red700,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    HemoamButton(
                        text = if (isLoading) {
                            if (isLogin) "Entrando..." else "Criando conta..."
                        } else {
                            if (isLogin) "Entrar" else "Criar Conta"
                        },
                        onClick = {
                            val validationError = validateForm()
                            if (validationError != null) {
                                errorMessage = validationError
                                return@HemoamButton
                            }

                            isLoading = true
                            if (isLogin) {
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val firebaseUser = task.result?.user
                                            val user = User(
                                                id = firebaseUser?.uid ?: "",
                                                name = firebaseUser?.displayName ?: "",
                                                email = firebaseUser?.email ?: "",
                                                provider = AuthProvider.EMAIL
                                            )
                                            onAuthSucess(user)
                                        } else {
                                            errorMessage = task.exception?.message ?: "Ocorreu um erro"
                                        }
                                        isLoading = false
                                    }
                            } else {
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val firebaseUser = task.result?.user
                                            val user = User(
                                                id = firebaseUser?.uid ?: "",
                                                name = name,
                                                email = email,
                                                provider = AuthProvider.EMAIL
                                            )
                                            onAuthSucess(user)
                                        } else {
                                            errorMessage = task.exception?.message ?: "Ocorreu um erro"
                                        }
                                        isLoading = false
                                    }
                            }
                        },
                        enabled = !isLoading,
                        backgroundColor = Red600
                    )
                }
            }

            // --- Alternar Login/Cadastro ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val prefixText = if (isLogin) "Não tem uma conta? " else "Já tem uma conta? "
                val clickableText = if (isLogin) "Criar conta" else "Entrar"

                Text(
                    text = buildAnnotatedString {
                        append(prefixText)
                        pushStyle(SpanStyle(color = Red600))
                        append(clickableText)
                        pop()
                    },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable {
                        isLogin = !isLogin
                        name = ""
                        email = ""
                        password = ""
                        confirmPassword = ""
                        errorMessage = null
                    }
                )
            }
        }
    }
}
