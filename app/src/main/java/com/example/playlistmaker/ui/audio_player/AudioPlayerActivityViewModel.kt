package com.example.playlistmaker.ui.audio_player

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioPlayerActivityViewModel : ViewModel() {
    private val _state = MutableStateFlow<AudioPlayerActivityState>(AudioPlayerActivityState.defaultState)
    val state = _state.asStateFlow()

    

    fun makeAction(action: AudioPlayerAction) {
        when(action) {
            is AudioPlayerAction.prepareTrack -> handlePrepareTrack(action)
            is AudioPlayerAction.playSongPreview -> handlePlaySongPreview()
            is AudioPlayerAction.pauseSongPreview -> handlePauseSongPreview()
        }
    }

    private fun handlePrepareTrack(action: AudioPlayerAction.prepareTrack) {
        TODO("Not yet implemented")
    }

    private fun handlePlaySongPreview() {
        TODO("Not yet implemented")
    }

    private fun handlePauseSongPreview() {
        TODO("Not yet implemented")
    }

}