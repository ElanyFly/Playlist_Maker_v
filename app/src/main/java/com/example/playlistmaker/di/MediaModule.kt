package com.example.playlistmaker.di

import com.example.playlistmaker.media.presentation.FavoriteTracksFragmentViewModel
import com.example.playlistmaker.media.presentation.PlaylistFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {

    viewModel<FavoriteTracksFragmentViewModel> {
        FavoriteTracksFragmentViewModel(
            favTracksInteractor = get()
        )
    }

    viewModel<PlaylistFragmentViewModel> {
        PlaylistFragmentViewModel(

        )
    }

}