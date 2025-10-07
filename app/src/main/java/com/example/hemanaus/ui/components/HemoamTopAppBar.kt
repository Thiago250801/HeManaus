package com.example.hemanaus.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hemoam.app.ui.theme.Red600
import com.hemoam.app.ui.theme.Green600 // Importação necessária para usar a cor verde

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HemoamTopAppBar(
    title: String,
    // Novo parâmetro de cor, com Red600 como padrão
    containerColor: Color = Red600,
    canNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title, color = Color.White) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )

                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                // Usando o novo parâmetro de cor
                containerColor = containerColor
            )

        )
    } else {
        TopAppBar(
            title = { Text(title, color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(
                // Usando o novo parâmetro de cor
                containerColor = containerColor
            )
        )
    }
}
