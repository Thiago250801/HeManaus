package com.example.hemanaus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hemanaus.core.viewmodel.BookingViewModel
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.HemoamTopAppBar
import com.example.hemanaus.ui.components.QRCodePlaceholder
import com.hemoam.app.ui.theme.Green600
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(
    bookingViewModel: BookingViewModel,
    // Função para ser chamada quando o usuário quiser voltar
    onBack: () -> Unit,
    // Função para ser chamada quando o check-in for concluído
    onComplete: () -> Unit
) {
    val booking = bookingViewModel.booking.value ?: return
    var isCheckingIn by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HemoamTopAppBar(
                title = "Check-in",
                canNavigateBack = true,
                onNavigateBack = onBack,
                containerColor = Green600
            )
        }
    ) { paddingValues ->
        // Lista rolável que compõe os itens de forma preguiçosa (só renderiza o que está na tela).
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- ITEM 1: MENSAGEM DE BOAS-VINDAS ---
            item {
                HemoamCard {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WavingHand,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Bem-vindo ao Hemoam!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        // Usa o nome do doador do objeto 'booking' para personalizar a mensagem
                        Text(
                            text = "Olá ${booking.name}, você chegou para sua doação",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // --- ITEM 2: QR CODE PARA CHECK-IN ---
            item {
                HemoamCard {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Escaneie o QR Code",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Mostre este código na recepção para fazer seu check-in",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        QRCodePlaceholder()

                        Text(
                            text = "ID: ${booking.id}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                }
            }

            // --- ITEM 3: DETALHES DO AGENDAMENTO ---
            item {
                HemoamCard {
                    Text(
                        text = "Detalhes do Agendamento",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CheckInDetailRow(
                            icon = Icons.Default.DateRange,
                            label = "Data",
                            value = booking.selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        )

                        CheckInDetailRow(
                            icon = Icons.Default.AccessTime,
                            label = "Horário",
                            value = booking.selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                        )

                        CheckInDetailRow(
                            icon = Icons.Default.Favorite,
                            label = "Tipo",
                            value = booking.donationType.displayName
                        )

                        CheckInDetailRow(
                            icon = Icons.Default.LocationOn,
                            label = "Local",
                            value = booking.location
                        )
                    }
                }
            }

            // Queue information
            item {
                HemoamCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Posição na fila",
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Você é o 3º da fila - tempo estimado: 15 min",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // What to expect
            item {
                HemoamCard {
                    Text(
                        text = "O que esperar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ProcessStep(
                            step = "1",
                            title = "Triagem",
                            description = "Verificação de documentos e questionário de saúde"
                        )

                        ProcessStep(
                            step = "2",
                            title = "Exame médico",
                            description = "Verificação de sinais vitais e aptidão"
                        )

                        ProcessStep(
                            step = "3",
                            title = "Coleta",
                            description = "Processo de doação (10-15 minutos)"
                        )

                        ProcessStep(
                            step = "4",
                            title = "Lanche",
                            description = "Descanso e hidratação pós-doação"
                        )
                    }
                }
            }

            // --- ITEM 6: BOTÃO DE CHECK-IN ---
            item {
                Button(
                    onClick = {
                        onComplete()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isCheckingIn // O botão é desabilitado enquanto 'isCheckingIn' for true.
                ) {
                    // Exibe um indicador de progresso circular se estiver fazendo check-in
                    if (isCheckingIn) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    // O texto do botão muda de acordo com o estado 'isCheckingIn'.
                    Text(
                        text = if (isCheckingIn) "Fazendo check-in..." else "Confirmar Check-in"
                    )
                }
            }

            // Help section
            item {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Precisa de ajuda?")
                }
            }
        }
    }
}

@Composable
private fun CheckInDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
// Um Composable para exibir um passo do processo de doação, com um número e descrição.
@Composable
private fun ProcessStep(
    step: String,
    title: String,
    description: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.size(24.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = step,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}