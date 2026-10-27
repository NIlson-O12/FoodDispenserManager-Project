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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaginaIntroduccionPreview() {
    PaginaintroduccionScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {}
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PaginaIntroduccionNightPreview() {
    PaginaintroduccionScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {}
    )
}

@Composable
fun PaginaintroduccionScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val imagenFondo = if (isSystemInDarkTheme()) {
        R.drawable.wallpaper_main_night2
    } else {
        R.drawable.wallpaper_main
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

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF5F5DC),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Iniciar Sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF5F5DC),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Crear Cuenta")
            }
        }
    }
}
