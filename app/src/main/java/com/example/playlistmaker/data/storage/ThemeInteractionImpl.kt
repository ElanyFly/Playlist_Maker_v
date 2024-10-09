package com.example.playlistmaker.data.storage

import com.example.playlistmaker.domain.ThemeInteraction

class ThemeInteractionImpl: ThemeInteraction {
    override fun getSwitchState(): Boolean {
        return SharedPreferencesManager.instance.getSwitchState()
    }

    override fun setSwitchState(isDarkTheme: Boolean) {
        SharedPreferencesManager.instance.saveSwitchState(isDarkTheme)
    }
}