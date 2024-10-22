package com.example.playlistmaker.di

import com.example.playlistmaker.settings.data.impl.ThemeInteractionImpl
import com.example.playlistmaker.settings.domain.ThemeInteraction
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.data.impl.IntentNavigationImpl
import com.example.playlistmaker.sharing.domain.Impl.SharingInteractionImpl
import com.example.playlistmaker.sharing.domain.SharingInteraction
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    viewModel<SettingsViewModel>{
        SettingsViewModel(
            intentNavigation = get(),
            themeInteraction = get()
        )
    }
    factory<SharingInteraction> {
        SharingInteractionImpl(
            intentNavigator = get(),
            context = get()
        )
    }

    factory<ThemeInteraction> {
        ThemeInteractionImpl(
            sharedPreferencesManager = get()
        )
    }

    factory<IntentNavigation> {
        IntentNavigationImpl(
            context = get()
        )
    }
}