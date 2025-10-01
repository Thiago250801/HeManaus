package com.example.hemanaus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hemanaus.core.model.Booking
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.HemoamTopAppBar
import com.example.hemanaus.ui.components.IconButton
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreparationScreen(
    booking: Booking,
    onBack: () -> Unit,
    onCheckIn: () -> Unit
) {
    Scaffold(
        topBar = {
            HemoamTopAppBar(
                title = "Preparação",
                canNavigateBack = true,
                onNavigateBack = onBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cartão de confirmação
            item {
                HemoamCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )

                        Column {
                            Text(
                                text = "Agendamento Confirmado!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Sua doação está agendada",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Booking details
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
                        DetailRow(
                            icon = Icons.Default.Person,
                            label = "Nome",
                            value = booking.name
                        )

                        DetailRow(
                            icon = Icons.Default.Favorite,
                            label = "Tipo",
                            value = booking.donationType.displayName
                        )

                        DetailRow(
                            icon = Icons.Default.DateRange,
                            label = "Data",
                            value = booking.selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        )

                        DetailRow(
                            icon = Icons.Default.AccessTime,
                            label = "Horário",
                            value = booking.selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                        )

                        DetailRow(
                            icon = Icons.Default.LocationOn,
                            label = "Local",
                            value = booking.location
                        )
                    }
                }
            }

            // Preparation tips
            item {
                HemoamCard {
                    Text(
                        text = "Dicas de Preparação",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PreparationTip(
                            icon = Icons.Default.WaterDrop,
                            tip = "Beba bastante água nas 24h antes da doação"
                        )

                        PreparationTip(
                            icon = Icons.Default.Restaurant,
                            tip = "Faça uma refeição leve até 3h antes"
                        )

                        PreparationTip(
                            icon = Icons.Default.Hotel,
                            tip = "Tenha uma boa noite de sono"
                        )

                        PreparationTip(
                            icon = Icons.Default.Badge,
                            tip = "Traga documento oficial com foto"
                        )

                        PreparationTip(
                            icon = Icons.Default.SmokeFree,
                            tip = "Evite fumar 2h antes da doação"
                        )
                    }
                }
            }

            // Reminder card
            item {
                HemoamCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Lembrete ativo",
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Você receberá uma notificação 1h antes",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = true,
                            onCheckedChange = { }
                        )
                    }
                }
            }

            // Check-in button
            item {
                IconButton(
                    icon = Icons.Default.QrCodeScanner,
                    label = "Fazer Check-in",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCheckIn
                )
            }

            // Additional actions
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Reagendar")
                    }

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
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

@Composable
private fun PreparationTip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tip: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = tip,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}