package com.example.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.domain.model.EmailData

class IntentNavigationImpl(
): IntentNavigation {

    val context: Context = Creator.provideContext()

    override fun shareLink(url: String) {

        val sendIntent = Intent(
            Intent.ACTION_SEND
        ).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(
            sendIntent, null
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }

    override fun sendEmail(emailData: EmailData) {
        val sendIntent = Intent(
            Intent.ACTION_SENDTO
        ).apply {
            setData(Uri.parse("mailto:"))
            putExtra(Intent.EXTRA_EMAIL, emailData.emailAddress)
            putExtra(Intent.EXTRA_SUBJECT, emailData.header)
            putExtra(Intent.EXTRA_TEXT, emailData.message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(sendIntent)
    }

    override fun openAgreement(url: String) {
        val showAgreement = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(showAgreement)
    }
}

