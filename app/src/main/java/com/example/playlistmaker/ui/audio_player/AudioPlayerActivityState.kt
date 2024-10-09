package com.example.playlistmaker.ui.audio_player

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.Constants

data class AudioPlayerActivityState (
    val track: Track,
    val playTime: String,
    val isPlaying: Boolean,
    val isPaused: Boolean,
    val isFinished: Boolean
) {
    companion object{
        val defaultState = AudioPlayerActivityState(
            track = Track(
                trackId = 0,
                trackName = "",
                artistName = "",
                trackTime = "",
                pictureURL = "",
                collectionName = "",
                releaseDate = "",
                primaryGenreName = "",
                country = "",
                previewUrl = ""
            ),
            playTime = Constants.PLAYER_TIME_DEFAULT,
            isPlaying = false,
            isPaused = true,
            isFinished = false
        )
    }
}