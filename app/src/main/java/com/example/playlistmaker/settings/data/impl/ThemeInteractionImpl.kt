package com.example.playlistmaker.settings.data.impl

import com.example.playlistmaker.common.SharedPreferencesManager
import com.example.playlistmaker.settings.domain.ThemeInteraction

class ThemeInteractionImpl(
    private val sharedPreferencesManager: SharedPreferencesManager
): ThemeInteraction {
    override fun getSwitchState(): Boolean {
        return sharedPreferencesManager.getSwitchState()
    }

    override fun setSwitchState(isDarkTheme: Boolean) {
        sharedPreferencesManager.saveSwitchState(isDarkTheme)
    }
}