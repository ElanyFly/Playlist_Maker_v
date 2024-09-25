package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.dto.TrackSearchRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val retrofit: Retrofit = getClient(BASE_URL)
    private val iTunesService = retrofit.create(TrackAPIService::class.java)

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

    override fun doRequest(dto: Any): Response {
        if (dto is TrackSearchRequest) {
            val thisSearch = iTunesService.searchTracks(dto.queryInput).execute()

            val body = thisSearch.body() ?: Response()
            return body.apply { responseCode = thisSearch.code() }
        } else {
            return Response().apply { responseCode = -1 }
        }
    }

    companion object {
        private const val BASE_URL = "https://itunes.apple.com"
    }
}