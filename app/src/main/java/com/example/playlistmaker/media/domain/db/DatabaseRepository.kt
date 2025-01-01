package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun addTrackToFav(track: Track)
    suspend fun deleteTrackFromFav(track: Track)
    fun getFavTracksList(): Flow<List<Track>>
}
