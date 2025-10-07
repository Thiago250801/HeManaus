package com.example.hemanaus.ui.components

import androidx.compose.foundation.BorderStroke // Importação necessária para a borda
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HemoamCard(
    modifier: Modifier = Modifier,
    // Cor do fundo do Card (Container Color)
    containerColor: Color = MaterialTheme.colorScheme.surface,
    // Cor da borda
    borderColor: Color? = null, // Torna opcional, padrão é sem borda
    // Largura da borda
    borderWidth: Dp = 1.dp, // Largura padrão caso a cor seja definida
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = containerColor // Usando a cor customizada ou padrão
        ),
        // Definição condicional da borda
        border = if (borderColor != null) {
            BorderStroke(borderWidth, borderColor)
        } else {
            null // Se borderColor for nulo, não há borda
        },
        onClick = onClick ?: {}
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            content = content
        )
    }
}

// ---

@Preview
@Composable
fun HemoamCardWithCustomBorderPreview() {
    // Exemplo usando cor de fundo customizada e borda primária
    HemoamCard(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        borderColor = MaterialTheme.colorScheme.primary,
        borderWidth = 2.dp
    ) {
        // Conteúdo
    }
}