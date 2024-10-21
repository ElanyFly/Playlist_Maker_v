package com.example.playlistmaker.audio_player.presentation

import com.example.playlistmaker.search.domain.models.Track
sealed interface AudioPlayerAction {

    class prepareTrack(val track: Track) : AudioPlayerAction
    class pressPlayBtn(val isStopped: Boolean = false) : AudioPlayerAction

}