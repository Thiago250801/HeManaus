package com.example.hemanaus.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label fixo acima do campo
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) Color.Black else Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                if (placeholder.isNotEmpty()) {
                    Text(placeholder)
                }
            },
            visualTransformation = visualTransformation,
            enabled = enabled,
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Gray,
                disabledContainerColor = Color(0xFFF5F5F5),
                disabledPlaceholderColor = Color.LightGray,
                disabledLabelColor = Color.LightGray,
                disabledIndicatorColor = Color.LightGray,

            )
        )
    }
}
