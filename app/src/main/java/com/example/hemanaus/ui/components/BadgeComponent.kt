package com.example.hemanaus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun BadgeComponent(
    text: String,
    icon: ImageVector? = null,
    backgroundColor: Color = Color.Gray,
    contentColor: Color? = Color.White,
    textSize: TextUnit = 12.sp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(backgroundColor, shape = CircleShape)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Ícone opcional
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor ?: MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(12.dp)
            )
        }

        // Texto
        if (contentColor != null) {
            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize),
                maxLines = 1
            )
        }
    }
}
