package com.teamwhite.food.dispenser.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.teamwhite.food.dispenser.manager.ui.theme.FoodDispenserManagerTheme
import kotlinx.coroutines.launch
import androidx.core.content.edit

class MainActivity : ComponentActivity() {

    private var keepSplashOnScreen = true
    private var pantallaInicial = "introduccion"

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }

        lifecycleScope.launch {
            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

            pantallaInicial = if (isLoggedIn) {
                "pagina_principal"
            } else {
                "introduccion"
            }

            keepSplashOnScreen = false
        }

        enableEdgeToEdge()

        setContent {
            FoodDispenserManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var pantallaActual by remember { mutableStateOf(pantallaInicial) }

                    when (pantallaActual) {
                        "introduccion" -> {
                            PaginaintroduccionScreen(
                                onNavigateToLogin = { pantallaActual = "iniciar_cuenta" },
                                onNavigateToRegister = { pantallaActual = "registrar_cuenta" }
                            )
                        }
                        "iniciar_cuenta" -> {
                            PaginainiciarcuentaScreen(
                                onRegresarClick = { pantallaActual = "introduccion" },
                                onLoginExitoso = {
                                    getSharedPreferences("AppPrefs", MODE_PRIVATE).edit {
                                        putBoolean(
                                            "isLoggedIn",
                                            true
                                        )
                                    }
                                    pantallaActual = "pagina_principal"
                                }
                            )
                        }
                        "registrar_cuenta" -> {
                            PaginaregistrarcuentaScreen(
                                onRegresarClick = { pantallaActual = "introduccion" },
                                onRegistroExitoso = { pantallaActual = "iniciar_cuenta" }
                            )
                        }
                        "pagina_principal" -> {
                            PaginaPrincipalScreen(
                                onNavigateToConfiguracion = { pantallaActual = "pagina_configuracion" },
                                onNavigateToRegistros = { pantallaActual = "pagina_registros" },
                                onNavigateToInformacion = { pantallaActual = "pagina_informacion" },
                                onCerrarSesion = {
                                    getSharedPreferences("AppPrefs", MODE_PRIVATE).edit {
                                        putBoolean(
                                            "isLoggedIn",
                                            false
                                        )
                                    }
                                    pantallaActual = "introduccion"
                                }
                            )
                        }
                        "pagina_informacion" -> {
                            PaginaInformacionScreen(
                                onBackToPrincipal = { pantallaActual = "pagina_principal" }
                            )
                        }
                        "pagina_configuracion" -> {
                            PaginaConfiguracionScreen(
                                onBackToPrincipal = { pantallaActual = "pagina_principal" }
                            )
                        }
                        "pagina_registros" -> {
                            PaginaRegistros(
                                onBackToPrincipal = { pantallaActual = "pagina_principal" }
                            )
                        }
                    }
                }
            }
        }
    }
}
