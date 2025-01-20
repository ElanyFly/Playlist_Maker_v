package com.example.playlistmaker.media.data.temporary

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun createNewPlaylist(playlist: PlaylistEntity) //TODO playlist model
    suspend fun updatePlaylist(playlist: PlaylistEntity)    //добавление и удаление треков вместе
    suspend fun deletePlaylist(playlist: PlaylistEntity)
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>
    fun getPlaylistTracksIds(): Flow<List<Int>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity //todo make model
    
}