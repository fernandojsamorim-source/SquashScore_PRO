package com.squashscore.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a live session record for the Supabase live_sessions table.
 */
data class LiveSessionRecord(
    @SerializedName("pin")
    val pin: String,

    @SerializedName("status")
    val status: String = "waiting",
)
