package com.example.playlistmaker.audio_player.domain

import androidx.lifecycle.LiveData
import com.example.playlistmaker.search.domain.models.Track

interface PlayerControl {
    val timeFlow: LiveData<String>
    val stateFlow: LiveData<StatePlayer>
    fun preparePlayer(track: Track)
    fun playbackControl(isStopped: Boolean)
    fun releasePlayer()
}