package com.example.hemanaus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hemanaus.core.model.Booking
import com.example.hemanaus.core.model.DonationType
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.HemoamTopAppBar
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    // Quando o usuário quiser voltar
    onBack: () -> Unit,
    // Quando o agendamento for concluído, passando os dados coletados.
    onComplete: (Booking) -> Unit
) {
    var currentStep by remember { mutableIntStateOf(1) }
    var selectedDonationType by remember { mutableStateOf(DonationType.BLOOD) }
    var selectedDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Scaffold(
        // Barra de título
        topBar = {
            HemoamTopAppBar(
                title = "Agendar Doação",
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
            //  INDICADOR DE PROGRESSO
            item {
                LinearProgressIndicator(
                    // O progresso é calculado dividindo o passo atual pelo total (3).
                    progress = { currentStep / 3f },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
                // Texto que mostra o passo atual.
                Text(
                    text = "Passo $currentStep de 3",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // --- CONTEÚDO DINÂMICO DOS PASSOS ---
            when (currentStep) {
                1 -> {
                    item {
                        DonationTypeStep(
                            selectedType = selectedDonationType,
                            onTypeSelected = { selectedDonationType = it }
                        )
                    }
                }
                2 -> {
                    item {
                        DateTimeStep(
                            selectedDate = selectedDate,
                            selectedTime = selectedTime,
                            onDateSelected = { selectedDate = it },
                            onTimeSelected = { selectedTime = it }
                        )
                    }
                }
                3 -> {
                    item {
                        PersonalInfoStep(
                            name = name,
                            email = email,
                            phone = phone,
                            onNameChanged = { name = it },
                            onEmailChanged = { email = it },
                            onPhoneChanged = { phone = it }
                        )
                    }
                }
            }

            // --- BOTÕES DE NAVEGAÇÃO ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (currentStep == 1) {
                        Arrangement.End
                    } else {
                        Arrangement.SpaceBetween
                    }
                ) {
                    // O botão "Voltar" só aparece a partir do passo 2.
                    if (currentStep > 1) {
                        OutlinedButton(
                            onClick = { currentStep-- } // Decrementa o passo para voltar.
                        ) {
                            Text("Voltar")
                        }
                    }
                    // Botão principal de ação.
                    Button(
                        onClick = {
                            if (currentStep < 3) {
                                currentStep++
                            }
                            // Se for o último passo, cria o objeto 'Booking' com todos os dados...
                            else {
                                val booking = Booking(
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    selectedDate = selectedDate,
                                    selectedTime = selectedTime,
                                    donationType = selectedDonationType
                                )
                                onComplete(booking)
                            }
                        }
                    ) {
                        Text(if (currentStep == 3) "Confirmar" else "Próximo")
                    }
                }
            }
        }
    }
}


@Composable
private fun DonationTypeStep(
    selectedType: DonationType,
    onTypeSelected: (DonationType) -> Unit
) {
    HemoamCard {
        Text(
            text = "Escolha o tipo de doação",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DonationType.values().forEach { donationType ->
                DonationTypeItem(
                    donationType = donationType,
                    selected = selectedType == donationType,
                    onSelected = { onTypeSelected(donationType) }
                )
            }
        }
    }
}

@Composable
private fun DonationTypeItem(
    donationType: DonationType,
    selected: Boolean,
    onSelected: () -> Unit
) {
    val (icon, description) = when (donationType) {
        DonationType.BLOOD -> Icons.Default.Favorite to "Doação completa de sangue total"
        DonationType.PLATELETS -> Icons.Default.Circle to "Doação específica de plaquetas"
        DonationType.PLASMA -> Icons.Default.LocalHospital to "Doação específica de plasma"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.RadioButton
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = donationType.displayName,
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

@Composable
private fun DateTimeStep(
    selectedDate: LocalDate,
    selectedTime: LocalTime,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HemoamCard {
            Text(
                text = "Selecione a data",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Data selecionada: ${selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Simplified date selection - in a real app you'd use a DatePicker
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(5) { index ->
                    val date = LocalDate.now().plusDays(index + 1L)
                    val isSelected = date == selectedDate

                    FilterChip(
                        onClick = { onDateSelected(date) },
                        label = { Text(date.format(DateTimeFormatter.ofPattern("dd/MM"))) },
                        selected = isSelected
                    )
                }
            }
        }

        HemoamCard {
            Text(
                text = "Selecione o horário",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Horário selecionado: ${selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Available time slots
            val timeSlots = listOf(
                LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0),
                LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(15, 0),
                LocalTime.of(16, 0), LocalTime.of(17, 0)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(timeSlots.size) { index ->
                    val time = timeSlots[index]
                    val isSelected = time == selectedTime

                    FilterChip(
                        onClick = { onTimeSelected(time) },
                        label = { Text(time.format(DateTimeFormatter.ofPattern("HH:mm"))) },
                        selected = isSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonalInfoStep(
    name: String,
    email: String,
    phone: String,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit
) {
    HemoamCard {
        Text(
            text = "Informações pessoais",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChanged,
                label = { Text("Nome completo") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChanged,
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                }
            )

            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChanged,
                label = { Text("Telefone") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null
                    )
                }
            )
        }
    }
}