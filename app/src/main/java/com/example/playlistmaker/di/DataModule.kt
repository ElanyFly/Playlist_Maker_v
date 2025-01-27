package com.example.playlistmaker.di

import androidx.room.Room
import com.example.playlistmaker.media.data.TracksRepositoryImpl
import com.example.playlistmaker.media.data.db.TracksDatabase
import com.example.playlistmaker.media.domain.db.TracksRepository
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.media.domain.impl.TracksInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TracksDatabase::class.java,
            "database.db"
        )
            .build()
    }

    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<TracksInteractor> {
        TracksInteractorImpl(get())
    }

}