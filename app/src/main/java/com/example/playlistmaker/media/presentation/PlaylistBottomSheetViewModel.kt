package com.example.playlistmaker.media.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.data.temporary.PlaylistInteractor
import com.example.playlistmaker.media.data.temporary.PlaylistTrackJoin
import com.example.playlistmaker.media.data.temporary.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistBottomSheetViewModel(
    private val interactor: PlaylistInteractor,
    private val trackInteractor: TracksInteractor
): ViewModel() {


    fun addTrackToPlaylist(playlistModel: PlaylistWithTracksEntity, track: Track) {
        val playlistJoin = PlaylistTrackJoin(
            playlistId = playlistModel.playlistEntity.playlistId,
            trackId = track.trackId
        )
        viewModelScope.launch(Dispatchers.IO) {
            trackInteractor.addIfNoTrack(track)
            interactor.insertConnection(playlistJoin)
        }

    }


}