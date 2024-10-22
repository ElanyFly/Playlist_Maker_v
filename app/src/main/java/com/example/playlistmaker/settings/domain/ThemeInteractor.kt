package com.example.playlistmaker.settings.domain

interface ThemeInteractor {
    fun getSwitchState(): Boolean
    fun setSwitchState(isDarkTheme: Boolean)
}