package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksInteractor {

    fun getFavTracksList(): Flow<List<Track>>
    suspend fun addTrack(track: Track)
    suspend fun deleteTrackFromFav(track: Track)
    suspend fun getFavStatus(trackId: Int): Boolean
    suspend fun addIfNoTrack(track: Track)
}