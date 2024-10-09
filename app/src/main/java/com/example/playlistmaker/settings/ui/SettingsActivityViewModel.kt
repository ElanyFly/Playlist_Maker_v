package com.example.playlistmaker.settings.ui

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.data.impl.ThemeInteractionImpl

class SettingsActivityViewModel : ViewModel() {
    private val themeInteractionImpl = ThemeInteractionImpl() //отправить в криатор

    fun switchTheme(isDarkTheme: Boolean) {
        themeInteractionImpl.setSwitchState(isDarkTheme)
    }

    fun getThemeState(): Boolean {
        return themeInteractionImpl.getSwitchState()
    }
}