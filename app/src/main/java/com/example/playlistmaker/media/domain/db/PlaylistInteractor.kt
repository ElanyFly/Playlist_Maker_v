package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun createNewPlaylist(playlist: PlaylistModel)
    suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoinModel): Boolean
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoinModel)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksModel>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksModel
//    suspend fun getTracksForPlaylist(playlistId: Int): List<Track>
//    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel>

}