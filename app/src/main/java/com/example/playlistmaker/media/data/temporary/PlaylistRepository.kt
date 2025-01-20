package com.example.playlistmaker.media.data.temporary

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun createNewPlaylist(playlist: PlaylistModel) //TODO playlist model
    suspend fun updatePlaylist(playlist: PlaylistModel)    //добавление и удаление треков вместе
    suspend fun deletePlaylist(playlist: PlaylistModel)
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>
    fun getPlaylistTracksIds(): Flow<List<Int>>
    suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity //todo make model

}