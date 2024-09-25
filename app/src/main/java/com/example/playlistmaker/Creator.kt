package com.example.playlistmaker

import com.example.playlistmaker.data.TrackRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkCreator
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.domain.SearchInteraction
import com.example.playlistmaker.domain.SearchInteractionImpl
import com.example.playlistmaker.domain.api.TrackInteractor
import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.impl.TrackInteractorImp
import retrofit2.Retrofit

object Creator {

    private const val BASE_URL = "https://itunes.apple.com"

    private val retrofitNetworkCreator: RetrofitNetworkCreator by lazy { RetrofitNetworkCreator() }
    private val retrofit: Retrofit by lazy { retrofitNetworkCreator.getClient(BASE_URL) }
    private val iTunesService by lazy { retrofit.create(TrackAPIService::class.java) }
    val searchInteraction: SearchInteraction by lazy { SearchInteractionImpl(iTunesService) }

    private fun getTrackRepository(): TrackRepository{
        return TrackRepositoryImpl(apiService = iTunesService)
    }

    fun provideTrackInteractor(): TrackInteractor {
        return TrackInteractorImp(getTrackRepository())
    }


}

