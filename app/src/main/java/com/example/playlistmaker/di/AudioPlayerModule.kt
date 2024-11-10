package com.example.playlistmaker.di

import com.example.playlistmaker.audio_player.presentation.AudioPlayerViewModel
import com.example.playlistmaker.audio_player.data.MediaPlayer
import com.example.playlistmaker.audio_player.domain.PlayerControl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val audioPlayerModule = module {

    viewModel<AudioPlayerViewModel> {
        AudioPlayerViewModel(
            mediaPlayer = get()
        )
    }

    factory<PlayerControl> {
        MediaPlayer()
    }
}