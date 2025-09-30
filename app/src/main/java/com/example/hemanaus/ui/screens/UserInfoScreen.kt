package com.example.hemanaus.ui.screens

import PhoneMaskTransformation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hemanaus.core.model.User
import com.example.hemanaus.ui.components.LabeledTextField
import com.example.hemanaus.ui.components.UserInfo
import com.hemoam.app.ui.theme.Red600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(
    user: User,
    onLogout: () -> Unit,
    onContinue: (fullName: String, phone: String) -> Unit
) {
    var fullName by remember { mutableStateOf(user.name) }
    var phone by remember { mutableStateOf("") }
    val isFormValid = fullName.isNotBlank() && phone.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Confirmar Dados",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Red600
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card UserInfo
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    UserInfo(
                        user = user,
                        onLogout = onLogout,
                        showActions = true
                    )
                }

                // Card Dados Complementares
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Dados Complementares",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "Complete suas informações para o agendamento",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Nome completo (editável)
                        LabeledTextField(
                            label = "Nome completo",
                            value = fullName,
                            onValueChange = { fullName = it },
                            enabled = true
                        )

                        // Email (desabilitado)
                        LabeledTextField(
                            label = "Email",
                            value = user.email,
                            onValueChange = {},
                            enabled = false
                        )

                        // Telefone (editável)
                        LabeledTextField(
                            label = "Telefone",
                            value = phone,
                            onValueChange = { if (it.length < 12) phone = it },
                            placeholder = "(92) 99999-9999",
                            visualTransformation = PhoneMaskTransformation()
                        )
                    }
                }

                // Botão Prosseguir
                Button(
                    onClick = { onContinue(fullName, phone) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red600,
                        disabledContainerColor = Red600.copy(alpha = 0.5f),
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.7f)
                    )
                ) {
                    Text(text = "Prosseguir")
                }
            }
        }
    )
}
