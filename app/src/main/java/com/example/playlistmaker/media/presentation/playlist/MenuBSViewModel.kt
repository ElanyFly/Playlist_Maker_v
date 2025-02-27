package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.sharing.domain.SharingInteractor

class MenuBSViewModel(
    private val sharingInteractor: SharingInteractor
): ViewModel() {

    fun sharePlaylist(playlist: PlaylistWithTracksModel) {
        sharingInteractor.sharePlaylist(playlist)
    }

}