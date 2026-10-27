package com.teamwhite.food.dispenser.manager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaginaInformacionPreview() {
    PaginaInformacionScreen(onBackToPrincipal = {})
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PaginaInformacionNightPreview() {
    PaginaInformacionScreen(onBackToPrincipal = {})
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PaginaInformacionScreen(onBackToPrincipal: () -> Unit) {
    var cantidadDeCuadros by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { cantidadDeCuadros })
    val coroutineScope = rememberCoroutineScope()

    val imagenFondo = if (isSystemInDarkTheme()) {
        R.drawable.wallpaper_main_night
    } else {
        R.drawable.wallpaper_main2
    }

    val colorBeige = Color(0xFFF5F5DC)

    val fondoBarra = if (isSystemInDarkTheme()) Color(0xFF1E1B24) else Color(0xFFFFF7FA)
    val colorTextoBarra = if (isSystemInDarkTheme()) Color.White else Color.Black

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Almacenamiento",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorTextoBarra
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackToPrincipal) {
                        Text(
                            text = "←",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorTextoBarra
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = fondoBarra
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight()
                    ) { page ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorBeige, RectangleShape)
                                .border(1.dp, Color.Black, RectangleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Almacen ${page + 1}",
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                    Button(
                        onClick = {
                            cantidadDeCuadros++
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(cantidadDeCuadros - 1)
                            }
                        },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = colorBeige),
                        modifier = Modifier
                            .width(50.dp)
                            .fillMaxHeight()
                            .border(1.dp, Color.Transparent, RectangleShape),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "+",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .border(1.dp, Color.Transparent, RectangleShape),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        val textoAlmacen = if (cantidadDeCuadros > 0) "${pagerState.currentPage + 1}" else "0"
                        Text(
                            text = "Abrir Almacen $textoAlmacen",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(colorBeige, RectangleShape)
                        .border(1.dp, Color.Transparent, RectangleShape),
                    contentAlignment = Alignment.Center
                ) {
                    val textoAlmacen = if (cantidadDeCuadros > 0) "${pagerState.currentPage + 1}" else "0"
                    Text(
                        text = "Informacion de Almacen $textoAlmacen",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
