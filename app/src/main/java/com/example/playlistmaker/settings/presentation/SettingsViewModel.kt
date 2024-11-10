package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.App
import com.example.playlistmaker.settings.domain.ThemeInteractor
import com.example.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    val intentNavigation: SharingInteractor,
    val themeInteractor: ThemeInteractor
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
        themeInteractor.setSwitchState(isDarkTheme)
        App.switchTheme(isDarkTheme)
    }

    fun getThemeState(): Boolean {
        return themeInteractor.getSwitchState()
    }
}