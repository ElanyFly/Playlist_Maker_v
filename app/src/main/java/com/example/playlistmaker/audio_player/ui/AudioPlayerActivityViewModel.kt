package com.example.playlistmaker.audio_player.ui

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayerActivityViewModel(
    coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
) : ViewModel() {
    private val _state =
        MutableStateFlow<AudioPlayerActivityState>(AudioPlayerActivityState.defaultState)
    val state = _state.asStateFlow()

    private var currentTrack: Track? = null

    private val mediaPlayer = Creator.mediaPlayerProvide()

    init {
        coroutineScope.launch {
            mediaPlayer.timeFlow.collect { time ->
                handleState(time = time)
            }
        }
        coroutineScope.launch {
            mediaPlayer.stateFlow.collect { statePlayer ->
                handleState(state = statePlayer)
            }
        }
    }

    fun makeAction(action: AudioPlayerAction) {
        when (action) {
            is AudioPlayerAction.prepareTrack -> handlePrepareTrack(action)
            is AudioPlayerAction.pressPlayBtn -> handlePressPlayBtn()
        }
    }

    private fun handlePressPlayBtn() {
        mediaPlayer.playbackControl()
    }

    private fun handlePrepareTrack(action: AudioPlayerAction.prepareTrack) {
        mediaPlayer.preparePlayer(action.track)
        currentTrack = action.track
        handleState(track = currentTrack)
    }

    private fun handleState(
        track: Track? = currentTrack,
        time: String = mediaPlayer.timeFlow.value,
        state: StatePlayer = mediaPlayer.stateFlow.value
    ) {
        _state.update {
            it.copy(
                track = track ?: it.track,
                playTime = time,
                isPlaying = state == StatePlayer.PLAYING,
                isPaused = state == StatePlayer.PAUSED,
                isFinished = state == StatePlayer.PREPARED

            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.releasePlayer()
    }
}