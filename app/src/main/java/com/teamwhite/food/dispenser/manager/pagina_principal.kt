package com.teamwhite.food.dispenser.manager

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaginaPrincipalPreview() {
    PaginaPrincipalScreen(
        onNavigateToConfiguracion = {},
        onNavigateToRegistros = {},
        onNavigateToInformacion = {},
        onCerrarSesion = {}
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PaginaPrincipalNightPreview() {
    PaginaPrincipalScreen(
        onNavigateToConfiguracion = {},
        onNavigateToRegistros = {},
        onNavigateToInformacion = {},
        onCerrarSesion = {}
    )
}

@Composable
fun PaginaPrincipalScreen(
    onNavigateToConfiguracion: () -> Unit,
    onNavigateToRegistros: () -> Unit,
    onNavigateToInformacion: () -> Unit,
    onCerrarSesion: () -> Unit,
    firebaseAuth: FirebaseAuth? = if (LocalInspectionMode.current) null else FirebaseAuth.getInstance()
) {
    val imagenFondo = if (isSystemInDarkTheme()) {
        R.drawable.wallpaper_main_night
    } else {
        R.drawable.wallpaper_main2
    }

    val colorBeige = Color(0xFFF5F5DC)
    var mostrarDialogo by remember { mutableStateOf(false) }

    val nombreUsuario = remember {
        firebaseAuth?.currentUser?.displayName ?: "Usuario"
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = imagenFondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Panel De Opciones",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Bienvenido, $nombreUsuario",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Button(
                onClick = onNavigateToConfiguracion,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBeige,
                    contentColor = Color.Black
                )
            ) {
                Text("Ver Configuración")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToRegistros,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBeige,
                    contentColor = Color.Black
                )
            ) {
                Text("Ver Registros")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToInformacion,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBeige,
                    contentColor = Color.Black
                )
            ) {
                Text("Ver Dispensadores")
            }

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedButton(
                onClick = { mostrarDialogo = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorBeige,
                    contentColor = Color.Black
                )
            ) {
                Text("Cerrar Sesión")
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = {
                    Text(text = "Confirmación")
                },
                text = {
                    Text(text = "¿Estás seguro de que deseas cerrar sesión?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogo = false
                            firebaseAuth?.signOut()
                            onCerrarSesion()
                        }
                    ) {
                        Text("Sí, cerrar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { mostrarDialogo = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
