package com.example.hemanaus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hemanaus.core.model.AuthProvider
import com.example.hemanaus.core.model.User
import com.hemoam.app.ui.theme.Blue100
import com.hemoam.app.ui.theme.Gray100
import com.hemoam.app.ui.theme.Gray600
import com.hemoam.app.ui.theme.Red100
import com.hemoam.app.ui.theme.Red600


@Composable
fun UserInfo(
    user: User,
    onLogout: () -> Unit,
    onEdit: (() -> Unit)? = null,
    showActions: Boolean = true,
    modifier: Modifier = Modifier
) {
    HemoamCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Avatar e informações
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Avatar ou iniciais
                if (user.avatar != null) {
                    AsyncImage(
                        model = user.avatar,
                        contentDescription = "Avatar do usuário",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Gray100)
                    )
                } else {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        color = Red100,
                        shape = CircleShape
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val initials = user.name.split(" ")
                                .map { it.first().uppercaseChar() }
                                .joinToString("")
                                .take(2)
                            Text(
                                text = initials,
                                style = MaterialTheme.typography.titleMedium,
                                color = Red600
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Nome e badge
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )

                        val Blue600 = null
                        BadgeComponent(
                            text = if (user.provider == AuthProvider.GOOGLE) "Google" else "Email",
                            backgroundColor = if (user.provider == AuthProvider.GOOGLE) Blue100 else Gray100,
                            contentColor = if (user.provider == AuthProvider.GOOGLE) Blue600 else Gray600,
                            icon = if (user.provider == AuthProvider.GOOGLE) Icons.Default.AccountCircle else Icons.Default.Email
                        )
                    }

                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Ações
            if (showActions) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    onEdit?.let {
                        IconButton(
                            onClick = it,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp, // <- substitui Logout
                            contentDescription = "Sair",
                            tint = Red600,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
