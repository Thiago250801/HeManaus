package com.example.hemanaus.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hemoam.app.ui.theme.Blue100
import com.hemoam.app.ui.theme.Blue500
import com.hemoam.app.ui.theme.Blue700
import com.hemoam.app.ui.theme.Green600
import com.hemoam.app.ui.theme.Red600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequirementsScreen(
    onBack: () -> Unit,
    onAceppted: () -> Unit
) {
    val requisitos = listOf(
        "Ter entre 16 e 69 anos",
        "Pesar no mínimo 50kg",
        "Estar em bom estado de saúde",
        "Ter dormido pelo menos 6 horas",
        "Não ter ingerido bebida alcoólica nas últimas 12 horas",
        "Estar alimentado (evitar alimentos gordurosos)"
    )

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
                        text = "Requisitos",
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
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Requisitos para Doação de Sangue",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        requisitos.forEach { requisito ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircleOutline,
                                    contentDescription = null,
                                    tint = Green600
                                )
                                Text(
                                    text = requisito,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = Blue100
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)){
                        Text(
                            text = "Importante: ",
                            style = MaterialTheme.typography.titleMedium,
                            color = Blue700
                        )
                        Text(
                            text = "Se você não atende a algum requisito, não se preocupe! Agende para uma data futura quando estiver apto.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Blue500
                        )
                    }
                }

                Button(
                    onAceppted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red600,
                        contentColor = Color.White,
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(text = "Atendo aos Requisitos",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp)
                }
            }

        }
    )
}

@Preview
@Composable
fun RequirementsScreenPreview() {
    RequirementsScreen(onBack = {}, onAceppted = {})
}