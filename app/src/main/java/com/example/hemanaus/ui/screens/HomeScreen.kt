package com.example.hemanaus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.hemanaus.core.model.StockStatus
import com.example.hemanaus.core.model.bloodStockLevel
import com.example.hemanaus.ui.components.BloodStockItem
import com.example.hemanaus.ui.components.HemoamCard
import com.example.hemanaus.ui.components.StatusBadge
import com.hemoam.app.ui.theme.*



@Composable
fun HomeScreen(
    onStartDonation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bloodStockData = bloodStockLevel()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Red50, Color.White)
                )
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Red600,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "HeManus",
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Salvando vidas em Manaus",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Red100,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Hero Image Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))

            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1697192156499-d85cfe1452c0",
                    contentDescription = "Doação de sangue",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sua doação pode salvar",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = "até 4 vidas",
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White
                        )
                    }
                }

            }
        }

        // Status Critico
        item {
            HemoamCard(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header com alerta
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Red100,
                                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Alerta de estoque crítico",
                                modifier = Modifier.size(22.dp),
                                tint = Red700
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Estoque Crítico",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Red700
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Red600,
                            tonalElevation = 2.dp,
                            shadowElevation = 4.dp
                        ) {
                            Text(
                                text = "URGENTE",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Subtítulo
                    Text(
                        text = "Baixo estoque de sangue no Hemoam/AM",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = Red700,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de estoques
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        bloodStockData.forEach { bloodStock ->
                            BloodStockItem(bloodStock = bloodStock)
                        }
                    }
                }
            }
        }

    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onStartDonation = {})
}