package com.squashscore.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkBackground = Color(0xFF080D0A)
private val NeonGreen = Color(0xFF00FF66)
private val DarkGreenButtonBg = Color(0xFF0A331D)
private val DarkButtonBg = Color(0xFF131C17)
private val DarkButtonBorder = Color(0xFF23352A)
private val TextGray = Color(0xFF7A9385)

/**
 * MainMenuScreen matching the SquashScore PRO startup menu design.
 */
@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    onNewMatchClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onScoreboardModeClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = DarkBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Branding Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "SQUASH",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    color = NeonGreen,
                    letterSpacing = 2.sp,
                )
                Text(
                    text = "S C O R E",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = TextGray,
                    letterSpacing = 8.sp,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Navigation Buttons List
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // NEW MATCH (Highlight Button)
                MenuButton(
                    text = "NEW MATCH",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = NeonGreen,
                            modifier = Modifier.size(28.dp),
                        )
                    },
                    backgroundColor = DarkGreenButtonBg,
                    borderColor = NeonGreen,
                    textColor = NeonGreen,
                    onClick = onNewMatchClick,
                )

                // HISTORY
                MenuButton(
                    text = "HISTORY",
                    iconText = "📋",
                    backgroundColor = DarkButtonBg,
                    borderColor = DarkButtonBorder,
                    textColor = Color.White,
                    onClick = onHistoryClick,
                )

                // SCOREBOARD MODE
                MenuButton(
                    text = "SCOREBOARD MODE",
                    iconText = "📺",
                    backgroundColor = DarkButtonBg,
                    borderColor = DarkButtonBorder,
                    textColor = Color.White,
                    onClick = onScoreboardModeClick,
                )

                // HELP
                MenuButton(
                    text = "HELP",
                    iconText = "?",
                    backgroundColor = DarkButtonBg,
                    borderColor = DarkButtonBorder,
                    textColor = Color.White,
                    onClick = onHelpClick,
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Footer Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Language Pill
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(DarkButtonBg)
                        .border(1.dp, DarkButtonBorder, CircleShape)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "🇺🇸 EN ⇄",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Settings Button
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(DarkButtonBg)
                        .border(1.dp, DarkButtonBorder, CircleShape)
                        .clickable { onSettingsClick() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = TextGray,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    iconText: String? = null,
    backgroundColor: Color = DarkButtonBg,
    borderColor: Color = DarkButtonBorder,
    textColor: Color = Color.White,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(10.dp))
            } else if (iconText != null) {
                Text(
                    text = iconText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Text(
                text = text,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainMenuScreenPreview() {
    MaterialTheme {
        MainMenuScreen()
    }
}
