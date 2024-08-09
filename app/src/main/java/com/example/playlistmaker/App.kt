package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = this
        val isDarkTheme = SharedPreferencesManager.getSwitchState()
        switchTheme(isDarkTheme)
    }

    companion object {
        lateinit var applicationContext: Application
        fun switchTheme(isDarkTheme: Boolean) {
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkTheme) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
            SharedPreferencesManager.saveSwitchState(isDarkTheme)
        }
    }

}