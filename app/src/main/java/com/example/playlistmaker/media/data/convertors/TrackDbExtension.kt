package com.example.playlistmaker.media.data.convertors

import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.search.data.dto.TrackDTO
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.convertMS


fun TrackDTO.toTrackEntity(): TrackEntity? {
    return TrackEntity(
        trackId = trackId ?: return null,
        trackName = trackName ?: return null,
        artistName = artistName ?: return null,
        trackTime = trackTime?.toLongOrNull() ?: 0,
        pictureURL = pictureURL ?: "",
        collectionName = collectionName ?: "",
        releaseDate = releaseDate ?: "",
        primaryGenreName = primaryGenreName ?: "",
        country = country ?: "",
        previewUrl = previewUrl ?: "",
        isFavourite = false
    )
}

fun Track.toTrackEntity(): TrackEntity {
    return TrackEntity(
        trackId = trackId,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        pictureURL = pictureURL,
        collectionName = collectionName,
        releaseDate = releaseDate,
        primaryGenreName = primaryGenreName,
        country = country,
        previewUrl = previewUrl,
        isFavourite = isFavorite
    )
}

fun TrackEntity.toTrack(): Track {
    return Track(
        trackId = trackId,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        pictureURL = pictureURL,
        collectionName = collectionName,
        releaseDate = releaseDate,
        primaryGenreName = primaryGenreName,
        country = country,
        previewUrl = previewUrl,
        isFavorite = isFavourite
    )
}

