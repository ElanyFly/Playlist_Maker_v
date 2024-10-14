package com.example.playlistmaker.audio_player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AudioPlayerViewModel() : ViewModel() {

    private val _playerState = MutableLiveData<AudioPlayerState>(AudioPlayerState.defaultState)
    val playerState: LiveData<AudioPlayerState>
        get() = _playerState


    private var currentTrack: Track? = null
    private val mediaPlayer = Creator.mediaPlayerProvide()


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
            }

        _playerState.postValue(newValue)

        }


    override fun onCleared() {
        super.onCleared()
        mediaPlayer.releasePlayer()
    }
}