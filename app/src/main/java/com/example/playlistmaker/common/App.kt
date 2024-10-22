package com.example.playlistmaker.common

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.di.audioPlayerModule
import com.example.playlistmaker.di.networkModule
import com.example.playlistmaker.di.searchModule
import com.example.playlistmaker.di.settingsModule
import com.example.playlistmaker.di.sharedPrefModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val sharedPrefManager: SharedPreferencesManager by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                sharedPrefModule,
                searchModule,
                networkModule,
                audioPlayerModule,
                settingsModule
            )
        }

        val isDarkTheme = sharedPrefManager.getSwitchState()
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