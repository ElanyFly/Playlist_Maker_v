package com.example.playlistmaker.sharing.domain.Impl

import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.domain.SharingInteractor
import com.example.playlistmaker.sharing.domain.SharingResources
import com.example.playlistmaker.sharing.domain.model.EmailData

class SharingInteractorImpl(
    private val intentNavigator: IntentNavigation,
    private val sharingResources: SharingResources
) : SharingInteractor {

    override fun shareLink() {
        intentNavigator.shareLink(getShareAppLink())
    }

    override fun sendEmail() {
        intentNavigator.sendEmail(getSupportEmailData())
    }

    override fun openAgreement() {
        intentNavigator.openAgreement(getAgreementLink())
    }

    override fun sharePlaylist(playlist: PlaylistWithTracksModel) {
        intentNavigator.sharePlaylist(playlist)
    }

    private fun getShareAppLink(): String {
        return sharingResources.courseLink
    }

    private fun getSupportEmailData(): EmailData {
        val email: Array<String> = arrayOf(sharingResources.helpdeskEmail)
        val header = sharingResources.helpdeskMailHeader
        val text = sharingResources.helpdeskMailMessage
        return EmailData(email, header, text)
    }

    private fun getAgreementLink(): String {
        return sharingResources.settingsAgreementLink
    }
}