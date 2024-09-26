package com.example.playlistmaker.ui.audio_player

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.convertMS

class MediaPlayer() {

    private lateinit var mediaPLayer: MediaPlayer
    private var playerState = StatePlayer.DEFAULT

    private val handler = Handler(Looper.getMainLooper())
    private val timeRunnable = Runnable {
        getCurrentTrackPosition()
        getPositionDelay()
    }


    private fun preparePlayer(track: Track) {
        mediaPLayer = MediaPlayer()
        with(mediaPLayer) {
            setDataSource(track.previewUrl)
            prepareAsync()
            setOnPreparedListener {
                playerState = StatePlayer.PREPARED
            }
            setOnCompletionListener {
                binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
                handler.removeCallbacks(timeRunnable)
                binding.trackTimeInProgress.text = Constants.PLAYER_TIME_DEFAULT
                playerState = StatePlayer.PREPARED
            }
        }
    }

    private fun startPlayer() {
        mediaPLayer.start()
        binding.btnPlay.setImageResource(R.drawable.audio_pausebutton)
        playerState = StatePlayer.PLAYING
        getPositionDelay()
    }

    private fun pausePlayer() {
        if (mediaPLayer.isPlaying) {
            mediaPLayer.pause()
        }
        binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
        playerState = StatePlayer.PAUSED
        handler.removeCallbacks(timeRunnable)
    }

    private fun playbackControl() {
        when (playerState) {
            StatePlayer.PLAYING -> pausePlayer()

            StatePlayer.PREPARED,
            StatePlayer.PAUSED -> startPlayer()

            StatePlayer.DEFAULT -> Unit
        }
    }

    private fun getCurrentTrackPosition() {
        binding.trackTimeInProgress.text = mediaPLayer.currentPosition.toLong().convertMS()
    }

    private fun getPositionDelay() {
        when(playerState) {
            StatePlayer.PLAYING -> {
                handler.removeCallbacks(timeRunnable)
                handler.postDelayed(timeRunnable, POSITION_DELAY)
            }
            StatePlayer.DEFAULT,
            StatePlayer.PREPARED,
            StatePlayer.PAUSED -> Unit
        }
    }

    companion object{
        private const val POSITION_DELAY = 300L
    }
}