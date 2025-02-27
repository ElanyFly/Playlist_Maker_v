package com.example.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoinEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewPlaylist(playlist: PlaylistEntity)

    @Insert(entity = PlaylistTrackJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity)

    @Query("DELETE FROM playlist_table WHERE playlistId = :playlistId")
    suspend fun deletePlaylist(playlistId: Int)

    @Delete(entity = PlaylistTrackJoinEntity::class)
    suspend fun deleteConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity)

    @Query("SELECT * FROM playlist_table ORDER BY timestamp DESC")
    fun getAllPlaylists(): List<PlaylistWithTracksEntity>

    @Transaction
    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    fun getPlaylistWithTracks(playlistId: Int): Flow<PlaylistWithTracksEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM playlist_track_join WHERE playlistId = :playlistId AND trackId = :trackId LIMIT 1)")
    suspend fun isConnectionExists(playlistId: Int, trackId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM playlist_track_join WHERE trackId = :trackId LIMIT 1)")
    suspend fun isTrackInAnyPlaylist(trackId: Int): Boolean

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    suspend fun getPlaylistById(playlistId: Int): PlaylistEntity

}