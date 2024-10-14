package com.example.playlistmaker.sharing.domain.Impl

import android.content.Context
import android.content.res.Resources
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.domain.SharingInteraction
import com.example.playlistmaker.sharing.domain.model.EmailData

class SharingInteractionImpl(
    private val intentNavigator: IntentNavigation
) : SharingInteraction {
    private val context: Context = Creator.provideContext()

    override fun shareLink() {
        intentNavigator.shareLink(getShareAppLink())
    }


    override fun sendEmail() {
        intentNavigator.sendEmail(getSupportEmailData())
    }

    override fun openAgreement() {
        intentNavigator.openAgreement(getAgreementLink())
    }

    private fun getShareAppLink(): String {
        return context.getString(R.string.android_developer_course_link)
    }

    private fun getSupportEmailData(): EmailData {
        val email: Array<String> = arrayOf(context.getString(R.string.helpdesk_email))
        val header = context.getString(R.string.helpdesk_mail_header)
        val text = context.getString(R.string.helpdesk_mail_message)
        return EmailData(email, header, text)
    }

    private fun getAgreementLink(): String {
        return context.getString(R.string.settings_agreement_link)
    }
}