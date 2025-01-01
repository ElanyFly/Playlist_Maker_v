package com.example.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.media.data.db.entity.TrackEntity

@Dao
interface TrackDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrackToFav (track: TrackEntity)

    @Delete
    suspend fun deleteTrackFromFav(track: TrackEntity)

    @Query("SELECT * FROM favourite_tracks")
    suspend fun getAllTracksInFav(): List<TrackEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_tracks WHERE trackId = :trackId)")
    suspend fun getFavStatus(trackId: Int): Boolean

    @Query("SELECT trackId FROM favourite_tracks")
    suspend fun getTrackIDsInFav(): List<Int>

}