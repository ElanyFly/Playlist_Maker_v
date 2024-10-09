package com.example.playlistmaker.settings.data.impl

import com.example.playlistmaker.data.storage.SharedPreferencesManager
import com.example.playlistmaker.settings.domain.ThemeInteraction

class ThemeInteractionImpl: ThemeInteraction {
    override fun getSwitchState(): Boolean {
        return SharedPreferencesManager.instance.getSwitchState()
    }

    override fun setSwitchState(isDarkTheme: Boolean) {
        SharedPreferencesManager.instance.saveSwitchState(isDarkTheme)
    }
}