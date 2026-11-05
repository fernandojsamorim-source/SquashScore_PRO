package com.squashscore.app.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.squashscore.app.data.api.SupabaseClient
import com.squashscore.app.data.model.LiveSessionRecord
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val BASE_URL = "https://fernandojsamorim-source.github.io/SquashScore_PRO/?sala="

/**
 * HostLobbyScreen Composable for generating room PINs and displaying the QR code for SquashScore_PRO.
 */
@Composable
fun HostLobbyScreen(
    modifier: Modifier = Modifier,
    player1Name: String = "Jogador 1",
    player2Name: String = "Jogador 2",
    supabaseUrl: String = "https://tosgwsmypmxkzhhaofqi.supabase.co",
    supabaseKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRvc2d3c215cG14a3poaGFvZnFpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTM4MjIyODcsImV4cCI6MjEwOTM5ODI4N30.WTRuZyHAZpWo_XZjntO746EyIrcBvlMYPf3re81rFbs",
    onBackClick: () -> Unit = {},
    onRoomCreated: (pin: String, url: String) -> Unit = { _, _ -> },
) {
    val coroutineScope = rememberCoroutineScope()
    var pin by rememberSaveable { mutableStateOf<String?>(null) }

    val fullUrl = remember(pin) {
        if (!pin.isNullOrEmpty()) "$BASE_URL$pin" else ""
    }

    val qrBitmap = remember(fullUrl) {
        if (fullUrl.isNotEmpty()) {
            generateQrCodeBitmap(fullUrl, size = 512)
        } else null
    }

    val createRoom: () -> Unit = {
        val newPin = String.format(Locale.US, "%04d", Random.nextInt(10000))
        pin = newPin
        onRoomCreated(newPin, "$BASE_URL$newPin")

        if (supabaseUrl.isNotEmpty() && supabaseKey.isNotEmpty()) {
            val playersList = listOf(player1Name, player2Name)
            createLiveSessionInSupabase(
                supabaseUrl = supabaseUrl,
                supabaseKey = supabaseKey,
                pin = newPin,
                allPlayers = playersList,
                courtPlayers = playersList,
                coroutineScope = coroutineScope,
            )
        } else {
            Log.d("HostLobbyScreen", "Supabase URL or Key is empty. Skipping network call.")
        }
    }

    LaunchedEffect(Unit) {
        if (pin == null) {
            createRoom()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Top Bar with Back Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Criar Sala",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$player1Name vs $player2Name",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "Host da Partida",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (pin == null) {
                // Initial state before room creation
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Gerando PIN numérico aleatório de 4 dígitos e QR Code...",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = createRoom,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Gerar PIN",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 4.dp),
                            )
                        }
                    }
                }
            } else {
                // State after room creation (displaying PIN and QR Code)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "SALA CONECTADA",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Large 4-digit PIN (e.g., 7788)
                        Text(
                            text = pin ?: "",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 8.sp,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "PIN da Sala",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // QR Code container (white background for optimal scanner contrast)
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(androidx.compose.ui.graphics.Color.White)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            qrBitmap?.let { bitmap ->
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "QR Code para entrar na sala $pin",
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Outros dispositivos podem escanear este QR Code para conectar:",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = fullUrl,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                            ),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedButton(
                            onClick = createRoom,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Gerar Nova Sala")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Inserts a new live session into Supabase using CoroutineScope and Retrofit.
 *
 * @param supabaseUrl Base URL of the Supabase project.
 * @param supabaseKey Anonymous/Public API key for Supabase.
 * @param pin Generated 4-digit room PIN.
 * @param coroutineScope CoroutineScope tied to the caller lifecycle.
 * @param onResult Callback executed with success status and optional message.
 */
fun createLiveSessionInSupabase(
    supabaseUrl: String,
    supabaseKey: String,
    pin: String,
    allPlayers: List<String> = emptyList(),
    courtPlayers: List<String> = emptyList(),
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    onResult: (success: Boolean, message: String) -> Unit = { _, _ -> },
) {
    coroutineScope.launch(Dispatchers.IO) {
        try {
            Log.d("HostLobbyScreen", "Attempting to create live session in Supabase for PIN: $pin with players: $allPlayers")
            val api = SupabaseClient.createApi(supabaseUrl)
            val record = LiveSessionRecord(
                pin = pin,
                allPlayers = allPlayers,
                courtPlayers = courtPlayers,
                score1 = 0,
                score2 = 0,
                status = "waiting",
            )

            val response = api.createLiveSession(
                apiKey = supabaseKey,
                authorization = "Bearer $supabaseKey",
                prefer = "return=representation",
                body = record,
            )

            if (response.isSuccessful) {
                Log.d("HostLobbyScreen", "Successfully created live session in Supabase for PIN: $pin")
                onResult(true, "Session created successfully")
            } else {
                val errorMsg = "Failed to create live session. Code: ${response.code()}, Error: ${response.errorBody()?.string()}"
                Log.e("HostLobbyScreen", errorMsg)
                onResult(false, errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = "Exception occurred while creating live session: ${e.localizedMessage}"
            Log.e("HostLobbyScreen", errorMsg, e)
            onResult(false, errorMsg)
        }
    }
}

/**
 * Pure utility function generating an ImageBitmap from the input string via ZXing Core.
 */
fun generateQrCodeBitmap(content: String, size: Int = 512): ImageBitmap? {
    return try {
        val hints: Map<EncodeHintType, Any> = mapOf(
            EncodeHintType.MARGIN to 1,
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
        )
        val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
        val pixels = IntArray(size * size)
        for (y in 0 until size) {
            val offset = y * size
            for (x in 0 until size) {
                pixels[offset + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun HostLobbyScreenPreview() {
    MaterialTheme {
        HostLobbyScreen()
    }
}
