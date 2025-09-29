package com.example.hemanaus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hemanaus.core.model.StockStatus
import com.hemoam.app.ui.theme.Green100
import com.hemoam.app.ui.theme.Green700
import com.hemoam.app.ui.theme.Orange100
import com.hemoam.app.ui.theme.Orange600
import com.hemoam.app.ui.theme.Red100
import com.hemoam.app.ui.theme.Red700

@Composable
fun StatusBadge(
    status: StockStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, text) = when (status) {
        StockStatus.CRITICAL -> Triple(Red100, Red700, "CRÍTICO")
        StockStatus.LOW -> Triple(Orange100, Orange600, "BAIXO")
        StockStatus.NORMAL -> Triple(Green100, Green700, "NORMAL")
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}