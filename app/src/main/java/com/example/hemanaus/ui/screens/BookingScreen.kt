import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hemanaus.core.model.Booking
import com.example.hemanaus.core.model.DonationType
import com.example.hemanaus.core.viewmodel.BookingViewModel
import com.example.hemanaus.ui.components.HemoamCard
import com.hemoam.app.ui.theme.Red600
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    bookingViewModel: BookingViewModel,
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    val user = bookingViewModel.user.value
    var selectedDonationType by remember { mutableStateOf(DonationType.BLOOD) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var location by remember { mutableStateOf("Hemoam - Manaus") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        "Agendamento",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Red600)
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

            // Card 1 - Tipo de Doação
            item {
                HemoamCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Tipo de Doação",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedDonationType.displayName,
                            onValueChange = {},
                            label = { Text("Selecione o tipo") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            DonationType.entries.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.displayName) },
                                    onClick = {
                                        selectedDonationType = type
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

// Card 2 - Escolha a Data
            item {
                HemoamCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Escolha a Data",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    var showDatePicker by remember { mutableStateOf(false) }

                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    }

                    if (showDatePicker) {
                        val today = Calendar.getInstance()
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                                showDatePicker = false
                            },
                            selectedDate.year,
                            selectedDate.monthValue - 1,
                            selectedDate.dayOfMonth
                        )

                        // Impede seleção de dias anteriores a hoje
                        datePickerDialog.datePicker.minDate = today.timeInMillis

                        datePickerDialog.show()
                    }
                }
            }

// Card 3 - Horário Disponível
            item {
                HemoamCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Horário Disponível",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de horários completa (8h às 16h)
                    val allSlots = gerarHorarios()

                    // Estado do horário selecionado
                    val selectedTimeState = remember { mutableStateOf(selectedTime) }

                    // Horário atual
                    val now = LocalTime.now()

                    // Renderização da grade de botões
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        allSlots.chunked(3).forEach { rowSlots ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowSlots.forEach { time ->
                                    // Desativar se for hoje e o horário já passou
                                    val isDisabled =
                                        selectedDate.isEqual(LocalDate.now()) && time.isBefore(now)

                                    val isSelected = time == selectedTimeState.value

                                    Button(
                                        onClick = {
                                            selectedTimeState.value = time
                                            selectedTime = time
                                        },
                                        enabled = !isDisabled,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = when {
                                                isDisabled -> Color.LightGray.copy(alpha = 0.4f)
                                                isSelected -> Red600
                                                else -> Color.Transparent
                                            },
                                            contentColor = when {
                                                isDisabled -> Color.Gray
                                                isSelected -> Color.White
                                                else -> MaterialTheme.colorScheme.onSurface
                                            }
                                        ),
                                        border = if (!isSelected && !isDisabled)
                                            BorderStroke(1.dp, Color.Gray)
                                        else null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                    ) {
                                        Text(
                                            time.format(DateTimeFormatter.ofPattern("HH:mm")),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }


            // Botão Confirmar
            item {
                Button(
                    onClick = {
                        bookingViewModel.setBooking(
                            Booking(
                                id = System.currentTimeMillis(),
                                name = user?.name ?: "",
                                email = user?.email ?: "",
                                phone = user?.phone ?: "",
                                selectedDate = selectedDate,
                                selectedTime = selectedTime,
                                donationType = selectedDonationType,
                                location = location
                            )
                        )
                        onComplete()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red600,
                        contentColor = Color.White,
                    ),
                ){
                    Text("Confirmar Agendamento", fontSize = 16.sp)
                }
            }
        }
    }
}

// Função para gerar horários de 30 em 30 min das 8 às 16h
fun gerarHorarios(): List<LocalTime> {
    val horarios = mutableListOf<LocalTime>()
    var hora = 8
    var minuto = 0
    while (hora < 16 || (hora == 16 && minuto == 0)) {
        horarios.add(LocalTime.of(hora, minuto))
        minuto += 30
        if (minuto == 60) {
            minuto = 0; hora++
        }
    }
    return horarios
}
