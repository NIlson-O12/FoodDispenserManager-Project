package com.teamwhite.food.dispenser.manager

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaginaRegistrosPreview() {
    PaginaRegistros(onBackToPrincipal = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaRegistros(onBackToPrincipal: () -> Unit) {
    val imagenFondo = if (isSystemInDarkTheme()) {
        R.drawable.wallpaper_main_night
    } else {
        R.drawable.wallpaper_main2
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registros") },
                navigationIcon = {
                    IconButton(onClick = onBackToPrincipal) {
                        Text(
                            text = "←",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = imagenFondo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        }
    }
}
