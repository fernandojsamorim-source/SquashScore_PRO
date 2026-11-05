package com.squashscore.app.data.api

import com.squashscore.app.data.model.LiveSessionRecord
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit interface for interacting with Supabase PostgREST endpoints.
 */
interface SupabaseApi {

    /**
     * Inserts a new live session record into the live_sessions table.
     *
     * @param apiKey Supabase anon/public key.
     * @param authorization Bearer authorization header with Supabase key.
     * @param prefer PostgREST Prefer header (e.g. return=representation).
     * @param body Session payload to be inserted.
     */
    @POST("rest/v1/live_sessions")
    suspend fun createLiveSession(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Prefer") prefer: String = "return=representation",
        @Body body: LiveSessionRecord,
    ): Response<List<LiveSessionRecord>>
}
