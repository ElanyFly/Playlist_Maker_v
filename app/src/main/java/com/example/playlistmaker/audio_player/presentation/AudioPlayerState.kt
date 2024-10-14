package com.example.playlistmaker.audio_player.presentation

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.Constants

data class AudioPlayerState (
    val track: Track,
    val playTime: String,
    val isPlaying: Boolean,
    val isPaused: Boolean,
    val isFinished: Boolean
) {
    companion object{
        val defaultState = AudioPlayerState(
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