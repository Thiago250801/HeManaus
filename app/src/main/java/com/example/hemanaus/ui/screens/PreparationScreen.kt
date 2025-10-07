package com.example.hemanaus.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.hemanaus.core.viewmodel.BookingViewModel
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.HemoamTopAppBar
import com.hemoam.app.ui.theme.Blue100
import com.hemoam.app.ui.theme.Blue500
import com.hemoam.app.ui.theme.Green100
import com.hemoam.app.ui.theme.Green600
import com.hemoam.app.ui.theme.Red200
import com.hemoam.app.ui.theme.Red600
import com.hemoam.app.ui.theme.Yellow100
import com.hemoam.app.ui.theme.Yellow600
import com.hemoam.app.ui.theme.Yellow800
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun openMap(context: Context, address: String) {
    val encodedAddress = Uri.encode(address)
    val gmmIntentUri = "geo:0,0?q=$encodedAddress".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

    mapIntent.setPackage("com.google.android.apps.maps")


    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        mapIntent.setPackage(null)
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            Log.e(
                "MapLauncher",
                "Erro: Nenhum aplicativo de mapa encontrado para abrir a localização."
            )
        }
    }
}

@Composable
fun PreparationScreen(
    bookingViewModel: BookingViewModel,
    onBack: () -> Unit,
    onCheckIn: () -> Unit
) {
    val booking = bookingViewModel.booking.value ?: return
    val scrollState = rememberScrollState()

    // Lista de dicas de preparação
    val preparationTips = listOf(
        Triple(Icons.Default.Hotel, "Durma bem", "Tenha pelo menos 6h de sono na noite anterior"),
        Triple(
            Icons.Default.Restaurant,
            "Alimente-se",
            "Faça uma refeição leve, evitando alimentos gordurosos"
        ),
        Triple(Icons.Default.WaterDrop, "Hidrate-se", "Beba bastante água antes da doação"),
        Triple(Icons.Default.Coffee, "Evite álcool", "Não consuma bebidas alcoólicas 12h antes")
    )

    // Lista de documentos necessários
    val whatToBring = listOf(
        "Documento oficial com foto (RG, CNH ou Passaporte)",
        "CPF ou Cartão Nacional de Saúde",
        "Comprovante de endereço recente"
    )

    val context = LocalContext.current
    val address = "Av. Constantino Nery, 4397 - Chapada, Manaus - AM"

    // Função para calcular tempo restante
    @RequiresApi(Build.VERSION_CODES.S)
    fun timeUntilAppointment(): String {
        val now = LocalDateTime.now()
        val appointmentDateTime = booking.selectedDate.atTime(booking.selectedTime)
        val duration = Duration.between(now, appointmentDateTime)

        return when {
            duration.isNegative -> "Hoje"
            duration.toHours() < 24 -> "${duration.toHours()}h ${duration.toMinutesPart()}min"
            else -> "${duration.toDays()} dias"
        }
    }

    Scaffold(
        topBar = {
            HemoamTopAppBar(title = "Preparação", canNavigateBack = true, onNavigateBack = onBack)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🕑 Contagem regressiva para a doação
            HemoamCard(
                containerColor = Red200,
                borderColor = Red600,
                borderWidth = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        "Próxima Doação",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                    Text(
                        timeUntilAppointment(),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "${booking.selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))} às ${
                            booking.selectedTime.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                        }",
                        color = MaterialTheme.colorScheme.error, fontSize = 14.sp
                    )
                }
            }

            // 📝 Detalhes do Agendamento
            HemoamCard(
                borderColor = Color.LightGray,
                borderWidth = 1.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.AccessTime, contentDescription = null)
                        Text("Detalhes do Agendamento", fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    DetailRow(
                        "Data e Hora",
                        "${booking.selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))} às ${
                            booking.selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                        }"
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    DetailRow("Tipo", booking.donationType.displayName)
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    DetailRow("Local", booking.location)

                    HemoamCard(
                        Modifier.padding(horizontal = 8.dp),
                        containerColor = Green100
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                address,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        OutlinedButton(
                            // CHAMA A FUNÇÃO CORRIGIDA
                            onClick = { openMap(context, address) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Map, contentDescription = null)
                                Text("Ver no Mapa", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // 🛡️ Dicas de Preparação
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
                        Icon(Icons.Outlined.Shield, contentDescription = null)
                        Text("Como se Preparar", fontWeight = FontWeight.Bold)
                    }
                    preparationTips.forEachIndexed { index, (icon, title, desc) ->
                        val isLastItem = index == preparationTips.lastIndex

                        // Define as cores com base se é o último item (Evite Álcool)
                        val badgeColor = if (isLastItem) Yellow100 else Blue100
                        val iconColor = if (isLastItem) Yellow600 else Blue500
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = androidx.compose.foundation.shape.CircleShape,
                                color = badgeColor,
                                modifier = Modifier.size(40.dp) // Tamanho do círculo
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = iconColor,
                                        modifier = Modifier.size(16.dp) // Tamanho do ícone
                                    )
                                }
                            }
                            Column {
                                Text(title, fontWeight = FontWeight.Medium)
                                Text(
                                    desc,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // 📑 Documentos Necessários
            HemoamCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color.LightGray,
                borderWidth = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Outlined.Warning, contentDescription = null)
                        Text("Documentos Necessários", fontWeight = FontWeight.Bold)
                    }
                    whatToBring.forEach { doc ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Green600)
                            Text(doc)
                        }
                    }
                }
            }

            // ✅ Botão Check-in
            Button(
                onClick = onCheckIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green600,
                )
            ) {
                Text("Fazer Check-in no Local")
            }

            // Info adicional
            Text(
                "Use este botão quando estiver no Hemoam/AM",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Medium)
    }
}


