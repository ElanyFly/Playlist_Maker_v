package com.example.playlistmaker.domain

interface ThemeInteraction {
    fun getSwitchState(): Boolean
    fun setSwitchState(isDarkTheme: Boolean)
}