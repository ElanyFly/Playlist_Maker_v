package com.example.playlistmaker.sharing.domain

interface SharingInteraction {
    fun shareLink()
    fun sendEmail()
    fun openAgreement()
}