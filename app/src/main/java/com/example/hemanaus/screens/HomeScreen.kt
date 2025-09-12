package com.example.hemanaus.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.hemanaus.data.BloodStock
import com.example.hemanaus.data.StockStatus
import com.example.hemanaus.ui.theme.Red100
import com.example.hemanaus.ui.theme.Red50
import com.example.hemanaus.ui.theme.Red600

@Composable
fun HomeScreen(
    onStartDonation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bloodStockData = listOf(
        BloodStock("A+", 15, StockStatus.CRITICAL),
        BloodStock("O-", 8, StockStatus.CRITICAL),
        BloodStock("B+", 25, StockStatus.LOW),
        BloodStock("AB-", 5, StockStatus.CRITICAL)
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Red50, Color.White)
                )
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        // Header
        item {
            Surface (
                modifier = Modifier.fillMaxWidth(),
                color = Red600,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ){
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )   {
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

        }
    }

}