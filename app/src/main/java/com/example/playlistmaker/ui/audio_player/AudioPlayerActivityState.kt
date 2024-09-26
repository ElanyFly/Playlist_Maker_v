package com.example.playlistmaker.ui.audio_player

import com.example.playlistmaker.domain.models.Track

data class AudioPlayerActivityState (
    val track: Track,
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
                collectionName = null,
                releaseDate = "",
                primaryGenreName = "",
                country = "",
                previewUrl = ""
            ),
            isPlaying = false,
            isPaused = false,
            isFinished = false
        )
    }
}