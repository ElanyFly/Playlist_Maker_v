package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.data.storage.SharedPreferencesManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = this
        val isDarkTheme = SharedPreferencesManager.instance.getSwitchState()
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
            SharedPreferencesManager.instance.saveSwitchState(isDarkTheme)
        }
    }

}