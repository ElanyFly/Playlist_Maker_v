package com.example.playlistmaker.settings.domain

interface SharedPreferencesTheme{
    fun saveSwitchState(isEnabled: Boolean)
    fun getSwitchState(): Boolean
}