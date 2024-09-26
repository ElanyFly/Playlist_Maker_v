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
                playerState = StatePlayer.PREPARED
            }
            setOnCompletionListener {
//                binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
                handler.removeCallbacks(timeRunnable)
                playerState = StatePlayer.PREPARED
            }
        }
    }

    private fun startPlayer() {
        mediaPLayer.start()
//        binding.btnPlay.setImageResource(R.drawable.audio_pausebutton)

        playerState = StatePlayer.PLAYING
        _stateFlow.update { playerState }
        getPositionDelay()
    }

    private fun pausePlayer() {
        if (mediaPLayer.isPlaying) {
            mediaPLayer.pause()
        }
//        binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
        playerState = StatePlayer.PAUSED
        _stateFlow.update { playerState }
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
        _timeFlow.update { mediaPLayer.currentPosition.toLong().convertMS() }
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
        mediaPLayer.release()
    }

    companion object {
        private const val POSITION_DELAY = 300L
    }
}