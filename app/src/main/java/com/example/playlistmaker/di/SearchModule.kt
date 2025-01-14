package com.example.playlistmaker.di

import com.example.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(
            searchInteractor = get()
        )
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(
            trackRepository = get(),
        )
    }

    single<TrackRepository> {
        TrackRepositoryImpl(
            apiService = get(),
            sharedPreferencesHistory = get(),
            favTracksDatabase = get(),
        )
    }

}