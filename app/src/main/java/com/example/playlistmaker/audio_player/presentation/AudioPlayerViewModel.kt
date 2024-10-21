package com.example.playlistmaker.audio_player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.search.domain.models.Track

class AudioPlayerViewModel(
    private val mediaPlayer: MediaPlayer
) : ViewModel() {

    private val _playerState = MutableLiveData<AudioPlayerState>(AudioPlayerState.defaultState)
    val playerState: LiveData<AudioPlayerState>
        get() = _playerState


    private var currentTrack: Track? = null

    init {
        mediaPlayer.timeFlow.observeForever { time ->
            handleState(time = time)
        }
        mediaPlayer.stateFlow.observeForever { statePlayer ->
            handleState(state = statePlayer)
        }

    }

    fun makeAction(action: AudioPlayerAction) {
        when (action) {
            is AudioPlayerAction.prepareTrack -> handlePrepareTrack(action)
            is AudioPlayerAction.pressPlayBtn -> handlePressPlayBtn(action)
        }
    }

    private fun handlePressPlayBtn(action: AudioPlayerAction.pressPlayBtn) {
        mediaPlayer.playbackControl(action.isStopped)
    }

    private fun handlePrepareTrack(action: AudioPlayerAction.prepareTrack) {
        mediaPlayer.preparePlayer(action.track)
        currentTrack = action.track

        handleState(track = currentTrack)
    }

    private fun handleState(
        track: Track? = currentTrack,
        time: String = mediaPlayer.timeFlow.value.toString(),
        state: StatePlayer = mediaPlayer.stateFlow.value ?: StatePlayer.PREPARED
    ) {
        val newValue = _playerState.value?.let {
            it.copy(
                track = track ?: it.track,
                playTime = time,
                isPlaying = state == StatePlayer.PLAYING,
                isPaused = state == StatePlayer.PAUSED,
                isFinished = state == StatePlayer.PREPARED

            )
        } ?: return

        _playerState.postValue(newValue)

        }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.releasePlayer()
    }
}