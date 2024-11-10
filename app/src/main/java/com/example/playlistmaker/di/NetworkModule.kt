package com.example.playlistmaker.di

import com.example.playlistmaker.search.data.network.RetrofitNetworkCreator
import com.example.playlistmaker.search.data.network.TrackAPIService
import com.example.playlistmaker.utils.Constants
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single<TrackAPIService> {
        RetrofitNetworkCreator()
            .getClient(Constants.BASE_URL)
            .create(TrackAPIService::class.java)
    }

}