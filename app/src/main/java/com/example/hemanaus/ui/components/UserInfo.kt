package com.example.hemanaus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hemanaus.core.model.AuthProvider
import com.example.hemanaus.core.model.User
import com.hemoam.app.ui.theme.Blue100
import com.hemoam.app.ui.theme.Blue600
import com.hemoam.app.ui.theme.Gray100
import com.hemoam.app.ui.theme.Gray600
import com.hemoam.app.ui.theme.Green100
import com.hemoam.app.ui.theme.Green600
import com.hemoam.app.ui.theme.Red100
import com.hemoam.app.ui.theme.Red600

private val Icons.Filled.Logout: ImageVector
    get() {
        TODO()
    }

@Composable
fun UserInfo(
    user: User,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    showActions: Boolean = true
) {
    HemoamCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Avatar
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
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.split(" ")
                                    .take(2)
                                    .map { it.first().uppercaseChar() }
                                    .joinToString(""),
                                style = MaterialTheme.typography.titleMedium,
                                color = Red600
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

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

                        StatusBadge(
                            text = user.provider.displayName,
                            backgroundColor = if (user.provider == AuthProvider.GOOGLE) Blue100 else Gray100,
                            contentColor = if (user.provider == AuthProvider.GOOGLE) Blue600 else Gray600,
                            icon = if (user.provider == AuthProvider.GOOGLE) Icons.Default.AccountCircle else Icons.Default.Email,
                            status = TODO(),
                            modifier = TODO()
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

            if (showActions) {
                IconButton(
                    onClick = onLogout,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Sair",
                        tint = Red600,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun AuthenticatedBadge(
    modifier: Modifier = Modifier
) {
    StatusBadge(
        text = "Autenticado",
        backgroundColor = Green100,
        contentColor = Green600,
        icon = Icons.Default.CheckCircle,
        modifier = modifier,
        status = TODO()
    )
}