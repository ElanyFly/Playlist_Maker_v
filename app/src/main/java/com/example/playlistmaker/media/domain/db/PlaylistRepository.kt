package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoin
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun createNewPlaylist(playlist: PlaylistModel)
    suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoin): Boolean
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoin)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity //todo make model
//    suspend fun getTracksForPlaylist(playlistId: Int): List<Track>
//    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel>

}