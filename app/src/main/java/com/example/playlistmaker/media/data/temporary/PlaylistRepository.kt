package com.example.playlistmaker.media.data.temporary

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun createNewPlaylist(playlist: PlaylistModel)
    //    suspend fun updatePlaylist(playlist: PlaylistModel)    //добавление и удаление треков вместе
    suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoin): Boolean
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoin)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>
//    fun getPlaylistTracksIds(): Flow<List<Int>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity //todo make model
    suspend fun getTracksForPlaylist(playlistId: Int): List<Track>
    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel>

}