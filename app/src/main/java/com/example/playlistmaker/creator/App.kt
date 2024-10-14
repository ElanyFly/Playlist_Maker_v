package com.example.playlistmaker.creator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.common.SharedPreferencesManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Creator.setContext(this)
        val isDarkTheme = SharedPreferencesManager.instance.getSwitchState()
        switchTheme(isDarkTheme)
    }

    companion object {
        fun switchTheme(isDarkTheme: Boolean) {
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkTheme) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }
}