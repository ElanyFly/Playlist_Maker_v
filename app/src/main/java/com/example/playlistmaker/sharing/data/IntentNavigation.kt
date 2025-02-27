package com.example.playlistmaker.sharing.data

import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.sharing.domain.model.EmailData

interface IntentNavigation {
    fun shareLink(url: String)
    fun sendEmail(emailData: EmailData)
    fun openAgreement(url: String)
    fun sharePlaylist(playlist: PlaylistWithTracksModel)
}