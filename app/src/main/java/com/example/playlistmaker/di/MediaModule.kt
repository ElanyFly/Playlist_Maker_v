package com.example.playlistmaker.di

import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.impl.PlaylistInteractorImpl
import com.example.playlistmaker.media.domain.db.PlaylistRepository
import com.example.playlistmaker.media.data.PlaylistRepositoryImpl
import com.example.playlistmaker.media.presentation.playlist.CreatePlaylistViewModel
import com.example.playlistmaker.media.presentation.favourite_tracks.FavoriteTracksFragmentViewModel
import com.example.playlistmaker.audio_player.presentation.PlaylistBottomSheetViewModel
import com.example.playlistmaker.media.presentation.playlist.PlaylistFragmentViewModel
import com.example.playlistmaker.media.presentation.playlist.PlaylistInfoViewModel
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
            playlistInteractor = get()
        )
    }

    viewModel<CreatePlaylistViewModel> {
        CreatePlaylistViewModel(
            playlistInteractor = get()
        )
    }

    viewModel<PlaylistBottomSheetViewModel> {
        PlaylistBottomSheetViewModel(
            playlistInteractor = get(),
            trackInteractor = get()
        )
    }

    viewModel<PlaylistInfoViewModel> {
        PlaylistInfoViewModel(
            playlistInteractor = get(),
            tracksInteractor = get()
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