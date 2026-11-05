package com.squashscore.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a live session record for the Supabase live_sessions table.
 */
data class LiveSessionRecord(
    @SerializedName("pin")
    val pin: String,

    @SerializedName("all_players")
    val allPlayers: List<String> = emptyList(),

    @SerializedName("court_players")
    val courtPlayers: List<String> = emptyList(),

    @SerializedName("score1")
    val score1: Int = 0,

    @SerializedName("score2")
    val score2: Int = 0,

    @SerializedName("status")
    val status: String = "waiting",
)
