package com.example.playlistmaker

import com.example.playlistmaker.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkCreator
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.data.storage.SharedPreferencesManager
import com.example.playlistmaker.data.storage.TrackStorageImpl
import com.example.playlistmaker.domain.SearchInteraction
import com.example.playlistmaker.domain.impl.SearchInteractionImpl
import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.api.TrackStorage
import com.example.playlistmaker.ui.audio_player.MediaPlayer
import retrofit2.Retrofit

object Creator {

    private const val BASE_URL = "https://itunes.apple.com"

    private val retrofitNetworkCreator: RetrofitNetworkCreator by lazy { RetrofitNetworkCreator() }
    private val retrofit: Retrofit by lazy { retrofitNetworkCreator.getClient(BASE_URL) }
    private val iTunesService by lazy { retrofit.create(TrackAPIService::class.java) }
    private val trackRepository: TrackRepository by lazy { TrackRepositoryImpl(iTunesService) }
    private val trackStorage: TrackStorage by lazy { TrackStorageImpl(sharedPreferencesManager = SharedPreferencesManager.instance) }

    private val searchInteraction: SearchInteraction by lazy {
        SearchInteractionImpl(
            trackRepository = trackRepository,
            trackStorage = trackStorage
        )
    }

    fun searchInteractionProvide(): SearchInteraction = searchInteraction
    fun trackStorageProvide(): TrackStorage = trackStorage
    fun mediaPlayerProvide(): MediaPlayer = MediaPlayer()

}

