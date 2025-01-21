package com.example.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.media.data.temporary.PlaylistTrackJoin
import com.example.playlistmaker.media.data.temporary.PlaylistWithTracksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewPlaylist(playlist: PlaylistEntity)

//    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
//    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Insert(entity = PlaylistTrackJoin::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoin)

    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)

    @Delete(entity = PlaylistTrackJoin::class)
    suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoin)

    @Query("SELECT * FROM playlist_table ORDER BY timestamp DESC")
    suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>>

//    @Query("SELECT * FROM playlist_table")        //not needed
//    suspend fun getPlaylistTracksIds(): List<Int>

    @Transaction
    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    suspend fun getPlaylistWithTracks(playlistId: Int)  : PlaylistWithTracksEntity

    @Query("SELECT * FROM saved_tracks INNER JOIN playlist_track_join ON trackId = trackId WHERE playlistId = :playlistId")
    suspend fun getTracksForPlaylist(playlistId: Int): List<TrackEntity>

    @Query("SELECT * FROM playlist_table INNER JOIN playlist_track_join ON playlistId = playlistId WHERE trackId = :trackId")
    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistEntity>
}