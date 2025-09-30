package com.example.hemanaus.ui.screens

import PhoneMaskTransformation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hemanaus.ui.components.LabeledTextField
import com.example.hemanaus.ui.components.UserInfo
import com.hemoam.app.ui.theme.Red600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequirementsScreen(
    onBack : () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        androidx.compose.material3.Icon(
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
                        Text(
                            text = "Para garantir a segurança do doador e do receptor, é importante atender aos seguintes requisitos:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "1. Idade: Entre 16 e 69 anos (menores de 18 anos precisam de autorização dos responsáveis).\n" +
                                    "2. Peso: Mínimo de 50 kg.\n" +
                                    "3. Saúde: Estar em boas condições de saúde no dia da doação.\n" +
                                    "4. Intervalo entre doações: Homens podem doar a cada 60 dias, mulheres a cada 90 dias.\n" +
                                    "5. Alimentação: Evitar alimentos gordurosos nas 3 horas que antecedem a doação.\n" +
                                    "6. Sono: Ter dormido pelo menos 6 horas nas últimas 24 horas.\n" +
                                    "7. Hidratação: Estar bem hidratado antes da doação.\n" +
                                    "8. Documentação: Apresentar um documento oficial com foto (RG, CNH, Carteira de Trabalho, etc.).",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    )
}