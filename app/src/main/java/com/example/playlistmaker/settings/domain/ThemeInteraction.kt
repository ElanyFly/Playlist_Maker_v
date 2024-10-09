package com.example.playlistmaker.settings.domain

interface ThemeInteraction {
    fun getSwitchState(): Boolean
    fun setSwitchState(isDarkTheme: Boolean)
}