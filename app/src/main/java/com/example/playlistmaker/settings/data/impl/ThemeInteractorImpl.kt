package com.example.playlistmaker.settings.data.impl

import com.example.playlistmaker.common.SharedPreferencesManager
import com.example.playlistmaker.settings.domain.ThemeInteractor

class ThemeInteractorImpl(
    private val sharedPreferencesManager: SharedPreferencesManager
): ThemeInteractor {
    override fun getSwitchState(): Boolean {
        return sharedPreferencesManager.getSwitchState()
    }

    override fun setSwitchState(isDarkTheme: Boolean) {
        sharedPreferencesManager.saveSwitchState(isDarkTheme)
    }
}