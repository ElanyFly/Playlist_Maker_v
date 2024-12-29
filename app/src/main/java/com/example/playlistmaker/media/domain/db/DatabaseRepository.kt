package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun addTrackToFav(track: Track)
    suspend fun deleteTrackFromFav(track: Track)
    fun getFavTracksList(): Flow<List<Track>>
}

/*
* метод для добавления трека в избранное;
метод для удаления трека из избранного;
метод получения списка со всеми треками, добавленными в избранное.
* Используйте Flow со списком треков в качестве возвращаемого типа данных для третьего метода.
* */