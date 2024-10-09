package com.example.playlistmaker.ui.settings

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.storage.ThemeInteractionImpl

class SettingsActivityViewModel : ViewModel() {
    private val themeInteractionImpl = ThemeInteractionImpl()

    fun switchTheme(isDarkTheme: Boolean) {
        themeInteractionImpl.setSwitchState(isDarkTheme)
    }

    fun getThemeState(): Boolean {
        return themeInteractionImpl.getSwitchState()
    }
}