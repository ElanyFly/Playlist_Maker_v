package com.example.playlistmaker.media.data.temporary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.TrackEntity

@Dao
interface PlaylistTrackJoinDao {

    @Insert
    suspend fun insert(playlistTrackJoin: PlaylistTrackJoin)

    @Delete
    suspend fun delete(playlistTrackJoin: PlaylistTrackJoin)

    @Query("SELECT * FROM saved_tracks INNER JOIN playlist_track_join ON trackId = trackId WHERE playlistId = :playlistId")
    suspend fun getTracksForPlaylist(playlistId: Int): List<TrackEntity>

    @Query("SELECT * FROM playlist_table INNER JOIN playlist_track_join ON playlistId = playlistId WHERE trackId = :trackId")
    suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistEntity>

}