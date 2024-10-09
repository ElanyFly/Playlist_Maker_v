package com.example.playlistmaker.ui.audio_player

import com.example.playlistmaker.domain.models.Track
sealed interface AudioPlayerAction {

    class prepareTrack(val track: Track) : AudioPlayerAction
    data object pressPlayBtn : AudioPlayerAction

}