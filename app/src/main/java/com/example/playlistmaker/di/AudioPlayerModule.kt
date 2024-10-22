package com.example.playlistmaker.di

import com.example.playlistmaker.audio_player.presentation.AudioPlayerViewModel
import com.example.playlistmaker.audio_player.presentation.MediaPlayer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val audioPlayerModule = module {

    viewModel<AudioPlayerViewModel> {
        AudioPlayerViewModel(
            mediaPlayer = get()
        )
    }

    factory<MediaPlayer> {
        MediaPlayer()
    }
}