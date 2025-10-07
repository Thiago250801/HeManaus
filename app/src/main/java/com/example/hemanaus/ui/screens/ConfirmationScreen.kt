package com.example.hemanaus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hemanaus.core.viewmodel.BookingViewModel
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.HemoamTopAppBar
import com.example.hemanaus.ui.theme.Typography
import com.hemoam.app.ui.theme.Blue100
import com.hemoam.app.ui.theme.Blue500
import com.hemoam.app.ui.theme.Blue700
import com.hemoam.app.ui.theme.Green600
import com.hemoam.app.ui.theme.Red600
import java.time.format.DateTimeFormatter

fun formatPhoneNumber(phone: String): String {
    // 1. Limpar: Remove todos os caracteres que não são dígitos.
    val digits = phone.filter { it.isDigit() }
    val length = digits.length

    // O telefone brasileiro (DDD + Número) tem 10 (antigo) ou 11 dígitos (padrão atual com '9' extra).
    if (length < 10 || length > 11) {
        return phone // Retorna o original se o comprimento for inválido (incluindo código de país).
    }

    // 2. Aplica a máscara
    return if (length == 10) {
        // Ex: (92) 1234-5678 (DDD + 8 dígitos)
        "(${digits.substring(0, 2)}) ${digits.substring(2, 6)}-${digits.substring(6, 10)}"
    } else { // length == 11
        // Ex: (92) 91234-5678 (DDD + 9 dígitos)
        "(${digits.substring(0, 2)}) ${digits.substring(2, 7)}-${digits.substring(7, 11)}"
    }
}

@Composable
fun ConfirmationScreen(
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    bookingViewModel: BookingViewModel,


    ) {

    val booking = bookingViewModel.booking.value ?: return
    Scaffold(
        topBar = {
            HemoamTopAppBar(
                title = "Confirmação",
                canNavigateBack = true,
                onNavigateBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HemoamCard(
                borderColor = Color.LightGray,
                borderWidth = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = Green600)
                        Text(
                            "Agendamento Confirmado",
                            style = Typography.titleMedium,
                            color = Green600
                        )
                    }
                    Column(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ConfirmationDetails(booking.name) {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                        ConfirmationDetails(booking.email) {
                            Icon(Icons.Outlined.Email, contentDescription = null, tint = Color.Gray)
                        }
                        ConfirmationDetails(formatPhoneNumber(booking.phone)) {
                            Icon(Icons.Outlined.Phone, contentDescription = null, tint = Color.Gray)
                        }
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                        ConfirmationDetails(
                            "${
                                booking.selectedDate.format(
                                    DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy"
                                    )
                                )
                            } às ${booking.selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
                        ) {
                            Icon(
                                Icons.Outlined.AccessTime,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                        ConfirmationDetails(booking.location) {
                            Icon(
                                Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }

                        HemoamCard(
                            modifier = Modifier.padding(top = 8.dp),
                            containerColor = Blue100
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Tipo:", color = Blue700, fontWeight = FontWeight.Bold)
                                Text(booking.donationType.displayName, color = Blue500)
                            }
                        }

                    }
                }
            }

            // Botão Confirmar
            Button(
                onConfirm, colors = ButtonDefaults.buttonColors(
                    containerColor = Red600,
                ), modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar Agendamento", fontWeight = FontWeight.Medium)
            }

        }
    }
}

@Composable
fun ConfirmationDetails(value: String, icon: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()
        Text(value, style = Typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
    }

}