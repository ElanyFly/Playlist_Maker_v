package com.example.playlistmaker.settings.data.impl

import com.example.playlistmaker.settings.domain.SharedPreferencesTheme
import com.example.playlistmaker.settings.domain.ThemeInteractor

class ThemeInteractorImpl(
    private val sharedPreferencesTheme: SharedPreferencesTheme
): ThemeInteractor {
    override fun getSwitchState(): Boolean {
        return sharedPreferencesTheme.getSwitchState()
    }

    override fun setSwitchState(isDarkTheme: Boolean) {
        sharedPreferencesTheme.saveSwitchState(isDarkTheme)
    }
}