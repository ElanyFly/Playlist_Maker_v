package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.creator.App
import com.example.playlistmaker.settings.domain.ThemeInteraction
import com.example.playlistmaker.sharing.domain.SharingInteraction

class SettingsViewModel(
    val intentNavigation: SharingInteraction,
    val themeInteraction: ThemeInteraction
) : ViewModel() {


    fun shareLink() {
        intentNavigation.shareLink()
    }

    fun sendEmail() {
        intentNavigation.sendEmail()
    }

    fun openAgreement() {
        intentNavigation.openAgreement()
    }


    fun switchTheme(isDarkTheme: Boolean) {
        themeInteraction.setSwitchState(isDarkTheme)
        App.switchTheme(isDarkTheme)
    }

    fun getThemeState(): Boolean {
        return themeInteraction.getSwitchState()
    }
}