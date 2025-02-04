package com.example.playlistmaker.audio_player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistBottomSheetViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val trackInteractor: TracksInteractor
): ViewModel() {


    fun addTrackToPlaylist(
        playlistModel: PlaylistWithTracksModel,
        track: Track,
        isExistLambda: (Boolean) -> Unit
    ) {
        val playlistJoin = PlaylistTrackJoinModel(
            playlistId = playlistModel.playlistId,
            trackId = track.trackId
        )
        viewModelScope.launch(Dispatchers.IO) {
            trackInteractor.addIfNoTrack(track)

            isExistLambda.invoke(
                playlistInteractor.insertConnection(playlistJoin)
            )
        }

    }


}