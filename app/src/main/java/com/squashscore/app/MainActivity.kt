package com.squashscore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.squashscore.app.ui.HostLobbyScreen

/**
 * Main Activity hosting the HostLobbyScreen.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HostLobbyScreen()
            }
        }
    }
}
