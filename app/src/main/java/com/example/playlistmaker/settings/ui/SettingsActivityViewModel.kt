package com.example.playlistmaker.settings.ui

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.creator.App
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.settings.data.impl.ThemeInteractionImpl

class SettingsActivityViewModel : ViewModel() {
    private val intentNavigation = Creator.settingsIntentProvide()
    private val themeInteractionImpl = ThemeInteractionImpl() //отправить в криатор


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
        themeInteractionImpl.setSwitchState(isDarkTheme)
        App.switchTheme(isDarkTheme)
    }

    fun getThemeState(): Boolean {
        return themeInteractionImpl.getSwitchState()
    }
}