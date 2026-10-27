package com.teamwhite.food.dispenser.manager

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaginaregistrarcuentaPreview() {
    PaginaregistrarcuentaScreen(onRegresarClick = {}, onRegistroExitoso = {})
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PaginaregistrarcuentaNightPreview() {
    PaginaregistrarcuentaScreen(onRegresarClick = {}, onRegistroExitoso = {})
}

@Composable
fun PaginaregistrarcuentaScreen(
    onRegresarClick: () -> Unit,
    onRegistroExitoso: () -> Unit,
    firebaseAuth: FirebaseAuth? = if (LocalInspectionMode.current) null else FirebaseAuth.getInstance()
) {
    val context = LocalContext.current
    var nombreUsuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var confirmarContrasenaVisible by remember { mutableStateOf(false) }

    val imagenFondo = if (isSystemInDarkTheme()) {
        R.drawable.wallpaper_main_night
    } else {
        R.drawable.wallpaper_main2
    }

    val colorBeige = Color(0xFFF5F5DC)
    val colorTextoOscuro = Color(0xFF3E2723)

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
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    TextButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                        Text(if (contrasenaVisible) "Ocultar" else "Ver")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (confirmarContrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    TextButton(onClick = { confirmarContrasenaVisible = !confirmarContrasenaVisible }) {
                        Text(if (confirmarContrasenaVisible) "Ocultar" else "Ver")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onRegresarClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBeige,
                        contentColor = colorTextoOscuro
                    )
                ) {
                    Text("Regresar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (nombreUsuario.isBlank() || correo.isBlank() || contrasena.isBlank() || confirmarContrasena.isBlank()) {
                            Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                        } else if (contrasena != confirmarContrasena) {
                            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        } else {
                            firebaseAuth?.createUserWithEmailAndPassword(correo.trim(), contrasena)
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val usuarioActual = firebaseAuth.currentUser
                                        val perfilActualizado = userProfileChangeRequest {
                                            displayName = nombreUsuario.trim()
                                        }

                                        usuarioActual?.updateProfile(perfilActualizado)
                                            ?.addOnCompleteListener { updateTask ->
                                                if (updateTask.isSuccessful) {
                                                    Toast.makeText(context, "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "Cuenta creada, pero no se pudo asignar el nombre", Toast.LENGTH_LONG).show()
                                                }
                                                onRegistroExitoso()
                                            }
                                    } else {
                                        val errorMsg = task.exception?.localizedMessage ?: "Error al registrar"
                                        Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBeige,
                        contentColor = colorTextoOscuro
                    )
                ) {
                    Text("Registrarse")
                }
            }
        }
    }
}
