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

class AudioPlayerViewModel(
    coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
) : ViewModel() {
//    private val _state =
//        MutableStateFlow<AudioPlayerState>(AudioPlayerState.defaultState)
//    val state = _state.asStateFlow()

    private val _playerState = MutableLiveData<AudioPlayerState>(AudioPlayerState.defaultState)
    val playerState: LiveData<AudioPlayerState>
        get() = _playerState


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
        time: String = mediaPlayer.timeFlow.value,
        state: StatePlayer = mediaPlayer.stateFlow.value
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

//        _state.update {
//            it.copy(
//                track = track ?: it.track,
//                playTime = time,
//                isPlaying = state == StatePlayer.PLAYING,
//                isPaused = state == StatePlayer.PAUSED,
//                isFinished = state == StatePlayer.PREPARED
//
//            )
        }


    override fun onCleared() {
        super.onCleared()
        mediaPlayer.releasePlayer()
    }
}