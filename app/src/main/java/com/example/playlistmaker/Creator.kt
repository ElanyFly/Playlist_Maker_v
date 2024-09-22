package com.example.playlistmaker

import com.example.playlistmaker.data.dto.TrackResponse
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.domain.SearchInteraction
import com.example.playlistmaker.domain.SearchInteractionImpl
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.ui.search.SearchAction
import com.example.playlistmaker.ui.search.SearchActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {
    private const val BASE_URL = "https://itunes.apple.com"

    private val retrofit: Retrofit by lazy { getClient(BASE_URL) }
    private val iTunesService by lazy { retrofit.create(TrackAPIService::class.java) }
    val searchInteraction: SearchInteraction by lazy { SearchInteractionImpl(iTunesService) }

    private fun getClient(baseURL: String): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }

        val newRetrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        return newRetrofit
    }
}

