package com.example.playlistmaker.di

import com.example.playlistmaker.common.SharedPreferencesManager
import com.example.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.search.data.storage.TrackStorageImpl
import com.example.playlistmaker.search.domain.SearchInteraction
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.api.TrackStorage
import com.example.playlistmaker.search.domain.impl.SearchInteractionImpl
import com.example.playlistmaker.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(
            searchInteraction = get()
        )
    }

    factory<SearchInteraction> {
        SearchInteractionImpl(
            trackRepository = get(),
            trackStorage = get()
        )
    }

    single<TrackStorage> {
        TrackStorageImpl(
            sharedPreferencesManager = get()
        )
    }

    factory<TrackRepository> {
        TrackRepositoryImpl(
            apiService = get()
        )
    }

}