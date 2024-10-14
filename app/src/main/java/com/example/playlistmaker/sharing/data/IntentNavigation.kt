package com.example.playlistmaker.sharing.data

import android.app.Activity
import com.example.playlistmaker.sharing.domain.model.EmailData

interface IntentNavigation {
    fun shareLink(url: String)
    fun sendEmail(emailData: EmailData)
    fun openAgreement(url: String)
}