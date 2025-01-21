package com.example.playlistmaker.media.data.temporary

import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun createNewPlaylist(playlist: PlaylistModel)
    suspend fun updatePlaylist(playlist: PlaylistModel)
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>
    fun getPlaylistTracksIds(): Flow<List<Int>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity

}