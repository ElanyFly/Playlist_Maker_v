package com.example.playlistmaker.di

import com.example.playlistmaker.settings.data.impl.ThemeInteractorImpl
import com.example.playlistmaker.settings.domain.ThemeInteractor
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.data.impl.IntentNavigationImpl
import com.example.playlistmaker.sharing.domain.Impl.SharingInteractorImpl
import com.example.playlistmaker.sharing.domain.SharingInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    viewModel<SettingsViewModel>{
        SettingsViewModel(
            intentNavigation = get(),
            themeInteractor = get()
        )
    }
    factory<SharingInteractor> {
        SharingInteractorImpl(
            intentNavigator = get(),
            context = get()
        )
    }

    factory<ThemeInteractor> {
        ThemeInteractorImpl(
            sharedPreferencesManager = get()
        )
    }

    factory<IntentNavigation> {
        IntentNavigationImpl(
            context = get()
        )
    }
}