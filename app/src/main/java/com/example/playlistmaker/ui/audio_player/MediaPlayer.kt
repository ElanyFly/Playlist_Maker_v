package com.example.playlistmaker.ui.audio_player

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.convertMS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MediaPlayer() {

    private lateinit var mediaPLayer: MediaPlayer
    private var playerState = StatePlayer.DEFAULT
    private var isReleased = false

    private val handler = Handler(Looper.getMainLooper())
    private val timeRunnable = Runnable {
        getCurrentTrackPosition()
        getPositionDelay()
    }

    private val _timeFlow = MutableStateFlow(Constants.PLAYER_TIME_DEFAULT)
    val timeFlow = _timeFlow.asStateFlow()

    private val _stateFlow = MutableStateFlow(playerState)
    val stateFlow = _stateFlow.asStateFlow()

    fun preparePlayer(track: Track) {
        mediaPLayer = MediaPlayer()
        with(mediaPLayer) {
            setDataSource(track.previewUrl)
            prepareAsync()
            setOnPreparedListener {
                setPlayerState(StatePlayer.PREPARED)
            }
            setOnCompletionListener {
                handler.removeCallbacks(timeRunnable)
                setPlayerState(StatePlayer.PREPARED)
                _timeFlow.update { Constants.PLAYER_TIME_DEFAULT }
            }
        }
    }

    private fun setPlayerState(playerState: StatePlayer){
        this.playerState = playerState
        _stateFlow.update { playerState }
    }
    private fun startPlayer() {
        mediaPLayer.start()
        setPlayerState(StatePlayer.PLAYING)
        getPositionDelay()
    }

    private fun pausePlayer() {
        if (mediaPLayer.isPlaying) {
            mediaPLayer.pause()
        }
        setPlayerState(StatePlayer.PAUSED)
        handler.removeCallbacks(timeRunnable)
    }

    fun playbackControl() {
        when (playerState) {
            StatePlayer.PLAYING -> pausePlayer()

            StatePlayer.PREPARED,
            StatePlayer.PAUSED -> startPlayer()

            StatePlayer.DEFAULT -> Unit
        }
    }

    fun getCurrentTrackPosition() {
        if (!isReleased){
            _timeFlow.update { mediaPLayer.currentPosition.toLong().convertMS() }
        }

    }

    fun getPositionDelay() {
        when (playerState) {
            StatePlayer.PLAYING -> {
                handler.removeCallbacks(timeRunnable)
                handler.postDelayed(timeRunnable, POSITION_DELAY)
            }

            StatePlayer.DEFAULT,
            StatePlayer.PREPARED,
            StatePlayer.PAUSED -> Unit
        }
    }

    fun releasePlayer() {
        isReleased = true
        mediaPLayer.release()
    }

    companion object {
        private const val POSITION_DELAY = 300L
    }
}