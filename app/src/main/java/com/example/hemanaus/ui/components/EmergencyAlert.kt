package com.example.hemanaus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hemoam.app.ui.theme.Red50
import com.hemoam.app.ui.theme.Red600
import com.hemoam.app.ui.theme.Red700

@Composable
fun EmergencyAlert(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Red50
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Red600,
                modifier = Modifier.size(24.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "EMERGÊNCIA MÉDICA",
                    fontWeight = FontWeight.Bold,
                    color = Red700,
                    fontSize = 14.sp
                )
                Text(
                    text = "Estoque crítico de sangue O- precisa de doadores urgentes",
                    color = Red600,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}