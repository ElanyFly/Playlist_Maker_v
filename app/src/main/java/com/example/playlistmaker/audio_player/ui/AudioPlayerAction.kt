package com.example.playlistmaker.audio_player.ui

import com.example.playlistmaker.search.domain.models.Track
sealed interface AudioPlayerAction {

    class prepareTrack(val track: Track) : AudioPlayerAction
    data object pressPlayBtn : AudioPlayerAction

}