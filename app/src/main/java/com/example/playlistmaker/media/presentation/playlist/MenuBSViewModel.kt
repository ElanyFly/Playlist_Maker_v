package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.sharing.domain.SharingInteractor
import kotlinx.coroutines.launch

class MenuBSViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val tracksInteractor: TracksInteractor,
    private val sharingInteractor: SharingInteractor
): ViewModel() {

    fun sharePlaylist(playlist: PlaylistWithTracksModel) {
        sharingInteractor.sharePlaylist(playlist)
    }

    fun deletePlaylist(playlist: PlaylistWithTracksModel) {
        viewModelScope.launch {
            playlistInteractor.deletePlaylist(playlist.playlistId)
            tracksInteractor.deleteOrphanedTracks()
        }
    }


}