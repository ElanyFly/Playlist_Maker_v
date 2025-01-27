package com.example.playlistmaker.di

import com.example.playlistmaker.media.data.temporary.PlaylistInteractor
import com.example.playlistmaker.media.data.temporary.PlaylistInteractorImpl
import com.example.playlistmaker.media.data.temporary.PlaylistRepository
import com.example.playlistmaker.media.data.temporary.PlaylistRepositoryImpl
import com.example.playlistmaker.media.presentation.CreatePlaylistViewModel
import com.example.playlistmaker.media.presentation.FavoriteTracksFragmentViewModel
import com.example.playlistmaker.media.presentation.PlaylistBottomSheetViewModel
import com.example.playlistmaker.media.presentation.PlaylistFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {

    viewModel<FavoriteTracksFragmentViewModel> {
        FavoriteTracksFragmentViewModel(
            tracksInteractor = get()
        )
    }

    viewModel<PlaylistFragmentViewModel> {
        PlaylistFragmentViewModel(

        )
    }

    viewModel<CreatePlaylistViewModel> {
        CreatePlaylistViewModel(
            playlistInteractor = get()
        )
    }

    viewModel<PlaylistBottomSheetViewModel> {
        PlaylistBottomSheetViewModel(
            interactor = get(),
            trackInteractor = get()
        )
    }

    factory<PlaylistInteractor> {
        PlaylistInteractorImpl(
            playlistRepository = get()
        )
    }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(
            tracksDatabase = get()
        )
    }


}