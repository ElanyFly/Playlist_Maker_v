package com.example.playlistmaker.media.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.media.data.db.dao.PlaylistDao
import com.example.playlistmaker.media.data.db.dao.TrackDao
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.media.data.temporary.PlaylistTrackJoin

@Database(version = 2, entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackJoin::class])
abstract class TracksDatabase: RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
}