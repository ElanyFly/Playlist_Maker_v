package com.example.playlistmaker.sharing.data

interface IntentNavigation {
    fun shareLink(url: String)
    fun sendEmail()
    fun openAgreement()
}