package com.example.playlistmaker.di

import androidx.room.Room
import com.example.playlistmaker.media.data.DatabaseRepositoryImpl
import com.example.playlistmaker.media.data.db.FavTracksDatabase
import com.example.playlistmaker.media.domain.db.DatabaseRepository
import com.example.playlistmaker.media.domain.db.FavTracksInteractor
import com.example.playlistmaker.media.domain.impl.FavTracksInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            FavTracksDatabase::class.java,
            "database.db"
        )
            .build()
    }

    single<DatabaseRepository> {
        DatabaseRepositoryImpl(get())
    }

    single<FavTracksInteractor> {
        FavTracksInteractorImpl(get())
    }

}