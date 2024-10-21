package com.example.playlistmaker.audio_player.presentation

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.convertMS

class MediaPlayer() {

    private lateinit var mediaPLayer: MediaPlayer
    private var playerState = StatePlayer.DEFAULT
    private var isReleased = false

    private val handler = Handler(Looper.getMainLooper())
    private val timeRunnable = Runnable {
        getCurrentTrackPosition()
        getPositionDelay()
    }

    private val _timeFlow = MutableLiveData(Constants.PLAYER_TIME_DEFAULT)
    val timeFlow: LiveData<String>
        get() = _timeFlow

    private val _stateFlow = MutableLiveData(playerState)
    val stateFlow: LiveData<StatePlayer>
        get() = _stateFlow

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
                _timeFlow.postValue(Constants.PLAYER_TIME_DEFAULT )
            }
        }
    }

    private fun setPlayerState(playerState: StatePlayer){
        this.playerState = playerState
        _stateFlow.postValue(playerState)
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

    fun playbackControl(isStopped: Boolean) {
        if (isStopped) {
            pausePlayer()
            return
        }
        when (playerState) {
            StatePlayer.PLAYING -> pausePlayer()

            StatePlayer.PREPARED,
            StatePlayer.PAUSED -> startPlayer()

            StatePlayer.DEFAULT -> Unit
        }
    }

    private fun getCurrentTrackPosition() {
        if (!isReleased){
            _timeFlow.postValue(mediaPLayer.currentPosition.toLong().convertMS() )
        }

    }

    private fun getPositionDelay() {
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