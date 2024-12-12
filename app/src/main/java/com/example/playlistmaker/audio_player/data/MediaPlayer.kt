package com.example.playlistmaker.audio_player.data

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.audio_player.domain.PlayerControl
import com.example.playlistmaker.audio_player.domain.StatePlayer
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.CoroutineScopes
import com.example.playlistmaker.utils.convertMS
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediaPlayer(
    private val scope: CoroutineScopes
) : PlayerControl {

    private lateinit var mediaPLayer: MediaPlayer
    private var playerState = StatePlayer.DEFAULT
    private var isReleased = false

    private var playerJob: Job? = null

    private val _timeFlow = MutableLiveData(Constants.PLAYER_TIME_DEFAULT)
    override val timeFlow: LiveData<String>
        get() = _timeFlow

    private val _stateFlow = MutableLiveData(playerState)
    override val stateFlow: LiveData<StatePlayer>
        get() = _stateFlow

    override fun preparePlayer(track: Track) {
        mediaPLayer = MediaPlayer()
        with(mediaPLayer) {
            setDataSource(track.previewUrl)
            prepareAsync()
            setOnPreparedListener {
                setPlayerState(StatePlayer.PREPARED)
            }
            setOnCompletionListener {
                playerJob?.cancel()
                setPlayerState(StatePlayer.PREPARED)
                _timeFlow.postValue(Constants.PLAYER_TIME_DEFAULT)
            }
        }
    }

    override fun playbackControl(isStopped: Boolean) {
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

    override fun releasePlayer() {
        isReleased = true
        mediaPLayer.release()
    }

    private fun setPlayerState(playerState: StatePlayer) {
        this.playerState = playerState
        _stateFlow.postValue(playerState)
    }

    private fun startPlayer() {
        mediaPLayer.start()
        setPlayerState(StatePlayer.PLAYING)

        playerJob?.cancel()
        playerJob = scope.mainScope.launch {
            while (mediaPLayer.isPlaying) {
                getCurrentTrackPosition()
                delay(POSITION_DELAY)
            }
        }
    }

    private fun pausePlayer() {
        if (mediaPLayer.isPlaying) {
            mediaPLayer.pause()
        }
        setPlayerState(StatePlayer.PAUSED)
        playerJob?.cancel()
    }

    private fun getCurrentTrackPosition() {
        if (!isReleased) {
            _timeFlow.postValue(mediaPLayer.currentPosition.toLong().convertMS())
        }

    }

    companion object {
        private const val POSITION_DELAY = 300L
    }
}