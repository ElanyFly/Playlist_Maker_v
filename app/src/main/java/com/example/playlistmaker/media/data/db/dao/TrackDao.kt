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
    suspend fun addTrack (track: TrackEntity)

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIfNoTrack (track: TrackEntity)

//    @Delete(entity = TrackEntity::class)
//    suspend fun deleteTrackFromBase(track: TrackEntity)

    @Query("DELETE FROM saved_tracks WHERE trackId = :trackId")
    suspend fun deleteTrackById(trackId: Int)

    @Query("UPDATE saved_tracks SET isFavourite = :isFavourite WHERE trackId = :trackId")
    suspend fun updateFavouriteStatus(trackId: Int, isFavourite: Boolean)

//    @Delete
//    suspend fun deleteTrackFromFav(track: TrackEntity)

    @Query("SELECT * FROM saved_tracks WHERE isFavourite IS 1 ORDER BY timestamp DESC")
    suspend fun getAllTracksInFav(): List<TrackEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_tracks WHERE trackId = :trackId AND isFavourite IS 1)")
    suspend fun getFavStatus(trackId: Int): Boolean

    @Query("SELECT trackId FROM saved_tracks")
    suspend fun getTrackIDsInFav(): List<Int>

}