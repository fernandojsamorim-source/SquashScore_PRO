package com.squashscore.app.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton client builder for initializing the Supabase Retrofit API instance.
 */
object SupabaseClient {

    /**
     * Creates a Retrofit instance targeting the provided Supabase base URL.
     *
     * @param baseUrl Supabase project URL (e.g. https://xyz.supabase.co/).
     */
    fun createApi(baseUrl: String): SupabaseApi {
        val formattedUrl = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(formattedUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApi::class.java)
    }
}
