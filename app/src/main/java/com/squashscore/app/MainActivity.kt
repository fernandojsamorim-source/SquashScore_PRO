package com.squashscore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.squashscore.app.ui.HostLobbyScreen
import com.squashscore.app.ui.MainMenuScreen
import com.squashscore.app.ui.NewMatchSetupScreen

/**
 * Sealed representation of top-level application screens.
 */
sealed interface AppScreen {
    data object MainMenu : AppScreen
    data object NewMatchSetup : AppScreen
    data class HostLobby(val player1: String, val player2: String) : AppScreen
}

/**
 * Main Activity hosting the SquashScore PRO application flow.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SquashScoreApp()
            }
        }
    }
}

@Composable
fun SquashScoreApp() {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.MainMenu) }

    when (val screen = currentScreen) {
        AppScreen.MainMenu -> {
            MainMenuScreen(
                onNewMatchClick = {
                    currentScreen = AppScreen.NewMatchSetup
                },
            )
        }

        AppScreen.NewMatchSetup -> {
            NewMatchSetupScreen(
                onBackClick = {
                    currentScreen = AppScreen.MainMenu
                },
            ) { p1, p2 ->
                currentScreen = AppScreen.HostLobby(player1 = p1, player2 = p2)
            }
        }

        is AppScreen.HostLobby -> {
            HostLobbyScreen(
                player1Name = screen.player1,
                player2Name = screen.player2,
                onBackClick = {
                    currentScreen = AppScreen.NewMatchSetup
                },
            )
        }
    }
}
