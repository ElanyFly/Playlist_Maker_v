package com.example.playlistmaker.sharing.domain

import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel

interface SharingInteractor {
    fun shareLink()
    fun sendEmail()
    fun openAgreement()
    fun sharePlaylist(playlist: PlaylistWithTracksModel)
}