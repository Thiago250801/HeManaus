package com.example.hemanaus.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.hemanaus.R
import com.example.hemanaus.core.model.AuthProvider
import com.example.hemanaus.core.model.User
import com.example.hemanaus.ui.components.HemoamButton
import com.example.hemanaus.ui.components.HemoamCard
import com.google.firebase.auth.FirebaseAuth
import com.hemoam.app.ui.theme.Blue50
import com.hemoam.app.ui.theme.Blue700
import com.hemoam.app.ui.theme.Blue800
import com.hemoam.app.ui.theme.Red50
import com.hemoam.app.ui.theme.Red600
import com.hemoam.app.ui.theme.Red700
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    onBackToHome: () -> Unit,
    onAuthSucess: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    var isLogin by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val firebaseAuth = FirebaseAuth.getInstance()

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

    Column(modifier = modifier.fillMaxSize()) {
        // Header
        Surface(modifier = Modifier.fillMaxWidth(), color = Red600) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isLogin) "Entrar" else "Criar Conta",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Login com Google
            HemoamCard {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Acesso Rápido", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Entre ou crie sua conta de forma rápida e segura",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botão Login com Google (oficial)
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                errorMessage = null
                                delay(2000)
                                val user = User(
                                    name = "João Silva",
                                    email = "joao.silva@gmail.com",
                                    avatar = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150",
                                    provider = AuthProvider.GOOGLE,
                                    id = "google-uid-123456"
                                )
                                onAuthSucess(user)
                                isLoading = false
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
                            tint = Color.Unspecified // mantém as cores originais
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isLoading) "Conectando..." else "Continuar com Google",
                            color = Color.Black
                        )
                    }

                }
            }

            // Separador
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    text = "ou continue com email",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            // Formulário de Login/Cadastro
            HemoamCard {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                        Surface(color = Red50, shape = RoundedCornerShape(8.dp)) {
                            Text(
                                text = errorMessage!!,
                                style = MaterialTheme.typography.bodySmall,
                                color = Red700,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
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

                            scope.launch {
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
                            }
                        },
                        enabled = !isLoading,
                        backgroundColor = Red600
                    )
                }
            }

            // Toggle entre Login e Cadastro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val prefixText = if (isLogin) "Não tem uma conta? " else "Já tem uma conta? "
                val clickableText = if (isLogin) "Criar conta" else "Entrar"

                val fullText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                        append(prefixText)
                    }

                    pushLink(LinkAnnotation.Clickable(tag = "toggle_action") {
                        isLogin = !isLogin
                        name = ""
                        email = ""
                        password = ""
                        confirmPassword = ""
                        errorMessage = null
                    })
                    withStyle(style = SpanStyle(color = Red600)) {
                        append(clickableText)
                    }
                    pop()
                }

                BasicText(
                    text = fullText,
                    style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
                )
            }

            // Informações de Segurança
            Surface(color = Blue50, shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Segurança:", style = MaterialTheme.typography.titleSmall, color = Blue800)
                    Text(
                        text = "Seus dados são protegidos e utilizados apenas para facilitar o processo de doação. Nunca compartilhamos informações pessoais com terceiros.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Blue700
                    )
                }
            }
        }
    }
}
