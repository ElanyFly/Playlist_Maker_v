package com.example.playlistmaker.creator

import android.annotation.SuppressLint
import android.content.Context
import com.example.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.search.data.network.RetrofitNetworkCreator
import com.example.playlistmaker.search.data.network.TrackAPIService
import com.example.playlistmaker.common.SharedPreferencesManager
import com.example.playlistmaker.search.data.storage.TrackStorageImpl
import com.example.playlistmaker.search.domain.SearchInteraction
import com.example.playlistmaker.search.domain.impl.SearchInteractionImpl
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.api.TrackStorage
import com.example.playlistmaker.audio_player.ui.MediaPlayer
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.data.impl.IntentNavigationImpl
import com.example.playlistmaker.sharing.domain.Impl.SharingInteractionImpl
import com.example.playlistmaker.sharing.domain.SharingInteraction
import retrofit2.Retrofit

@SuppressLint("StaticFieldLeak")
object Creator {

    private const val BASE_URL = "https://itunes.apple.com"

    private lateinit var context: Context
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

    private val intentNavigation: IntentNavigation by lazy {
        IntentNavigationImpl()
    }

    private val sharingInteraction: SharingInteraction by lazy {
        SharingInteractionImpl(intentNavigation)
    }

    fun settingsIntentProvide(): SharingInteraction = sharingInteraction

    fun searchInteractionProvide(): SearchInteraction = searchInteraction
    fun trackStorageProvide(): TrackStorage = trackStorage
    fun mediaPlayerProvide(): MediaPlayer = MediaPlayer()
    fun setContext(context: Context) {
        Creator.context = context
    }
    fun provideContext(): Context {
        return context
    }

}

