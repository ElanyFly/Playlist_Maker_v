package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.sharing.domain.SharingInteractor

class MenuBSViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val sharingInteractor: SharingInteractor
): ViewModel() {



}