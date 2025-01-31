package com.example.playlistmaker.media.domain.db

import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoinEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun createNewPlaylist(playlist: PlaylistModel)
    suspend fun insertConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity): Boolean
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun deleteConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity
//    suspend fun getTracksForPlaylist(playlistId: Int): List<Track>
//    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel>

}