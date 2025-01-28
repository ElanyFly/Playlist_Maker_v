package com.example.playlistmaker.media.data.temporary

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun createNewPlaylist(playlist: PlaylistModel)
    suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoin): Boolean
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoin)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity
    suspend fun getTracksForPlaylist(playlistId: Int): List<Track>
    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel>

}