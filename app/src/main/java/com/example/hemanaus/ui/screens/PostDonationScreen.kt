package com.example.hemanaus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hemanaus.core.model.Achievement
import com.example.hemanaus.core.model.Booking
import com.example.hemanaus.core.model.DonationHistory
import com.example.hemanaus.core.model.Impact
import com.example.hemanaus.core.viewmodel.BookingViewModel
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.HemoamTopAppBar
import com.example.hemanaus.ui.components.ProgressIndicator
import com.hemoam.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDonationScreen(
    bookingViewModel: BookingViewModel,
    onBack: () -> Unit,
    onNewDonation: () -> Unit
) {
    val booking = bookingViewModel.booking.value ?: return
    val impact = remember {
        Impact(
            livesSaved = 4,
            totalDonations = 3,
            nextEligibleDate = LocalDate.now().plusDays(60),
            rank = "Herói de Bronze",
            points = 75
        )
    }

    val achievements = remember {
        listOf(
            Achievement("Primeira Doação", "Fez sua primeira doação de sangue", true),
            Achievement("Salvando Vidas", "Salvou 3 vidas com suas doações", true),
            Achievement("Constante", "Fez 3 doações consecutivas", false),
            Achievement("Herói de Prata", "Alcançou 100 pontos", false)
        )
    }

    val donationHistory = remember {
        listOf(
            DonationHistory(LocalDate.now(), "Sangue Total", "Hemoam/AM - Manaus", 4),
            DonationHistory(LocalDate.now().minusDays(90), "Sangue Total", "Hemoam/AM - Manaus", 4),
            DonationHistory(LocalDate.now().minusDays(180), "Plaquetas", "Hemoam/AM - Manaus", 2)
        )
    }

    Scaffold(
        topBar = {
            HemoamTopAppBar(
                title = "Parabéns!",
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
            // Celebration header
            item {
                HemoamCard {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Celebration,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(64.dp)
                        )

                        Text(
                            text = "Doação Concluída!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Obrigado por salvar vidas, ${booking.name}!",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Impact statistics
            item {
                HemoamCard {
                    Text(
                        text = "Seu Impacto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ImpactStatistic(
                            value = impact.livesSaved.toString(),
                            label = "Vidas Salvas",
                            icon = Icons.Default.Favorite,
                            color = Red600
                        )

                        ImpactStatistic(
                            value = impact.totalDonations.toString(),
                            label = "Doações",
                            icon = Icons.Default.LocalHospital,
                            color = Blue600
                        )

                        ImpactStatistic(
                            value = impact.points.toString(),
                            label = "Pontos",
                            icon = Icons.Default.Stars,
                            color = Yellow600
                        )
                    }
                }
            }

            // Rank and progress
            item {
                HemoamCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    color = Orange100,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = Orange600,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = impact.rank,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${impact.points}/100 pontos para Herói de Prata",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            ProgressIndicator(
                                progress = impact.points / 100f,
                                label = ""
                            )
                        }
                    }
                }
            }

            // Achievements
            item {
                HemoamCard {
                    Text(
                        text = "Conquistas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(achievements) { achievement ->
                            AchievementBadge(achievement = achievement)
                        }
                    }
                }
            }

            // Next eligible date
            item {
                HemoamCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Próxima doação disponível",
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = impact.nextEligibleDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        OutlinedButton(
                            onClick = { }
                        ) {
                            Text("Lembrete")
                        }
                    }
                }
            }

            // Donation history
            item {
                HemoamCard {
                    Text(
                        text = "Histórico de Doações",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        donationHistory.take(3).forEach { donation ->
                            HistoryItem(donation = donation)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Ver histórico completo")
                    }
                }
            }

            // Share section
            item {
                HemoamCard {
                    Text(
                        text = "Compartilhe sua boa ação",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Inspire outros a doarem sangue e salvarem vidas",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Compartilhar")
                        }

                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Certificado")
                        }
                    }
                }
            }

            // Action buttons
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onNewDonation,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Nova Doação")
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Voltar ao Início")
                    }
                }
            }
        }
    }
}

@Composable
private fun ImpactStatistic(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = color.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AchievementBadge(
    achievement: Achievement
) {
    Surface(
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (achievement.unlocked) Green100 else Gray100,
        border = if (achievement.unlocked) {
            androidx.compose.foundation.BorderStroke(1.dp, Green600)
        } else null
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = if (achievement.unlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                contentDescription = null,
                tint = if (achievement.unlocked) Green600 else Gray400,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = achievement.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (achievement.unlocked) Green700 else Gray500,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun HistoryItem(
    donation: DonationHistory
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = Red100,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Red600,
                modifier = Modifier.size(16.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = donation.type,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${donation.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))} • ${donation.location}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "+${donation.impact}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Green600
        )
    }
}