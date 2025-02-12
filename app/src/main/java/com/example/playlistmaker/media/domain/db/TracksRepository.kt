package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {

    suspend fun addTrack(track: Track)
    suspend fun addIfNoTrack(track: Track)
    suspend fun deleteTrackById(trackId: Int)
    suspend fun updateFavouriteStatus(trackId: Int, isFavourite: Boolean)
    suspend fun isTrackExists(trackId: Int): Boolean

//    suspend fun deleteTrackFromFav(track: Track)
    fun getFavTracksList(): Flow<List<Track>>
    suspend fun getFavStatus(trackId: Int): Boolean
}
