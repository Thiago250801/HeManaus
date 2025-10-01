package com.example.hemanaus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hemoam.app.ui.theme.Gray100
import com.hemoam.app.ui.theme.Gray400
import com.hemoam.app.ui.theme.Gray500


@Composable
fun QRCodePlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .background(
                color = Gray100,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = "QR Code",
                modifier = Modifier.size(64.dp),
                tint = Gray400
            )
            Text(
                text = "QR Code",
                color = Gray500,
                fontSize = 14.sp
            )
        }
    }
}
