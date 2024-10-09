package com.example.playlistmaker.sharing.data

import android.app.Activity

interface IntentNavigation {
    fun shareLink(url: String)
    fun sendEmail()
    fun openAgreement()
}