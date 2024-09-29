package com.example.playlistmaker.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.Creator
import com.example.playlistmaker.data.storage.SharedPreferencesManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Creator.setContext(this)
//        Companion.applicationContext = this
        val isDarkTheme = SharedPreferencesManager.instance.getSwitchState()
        switchTheme(isDarkTheme)
    }

    companion object {
//        lateinit var applicationContext: Application
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