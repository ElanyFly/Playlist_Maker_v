package com.example.playlistmaker.data.mappers

import com.example.playlistmaker.data.dto.TrackDTO
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.convertMS

fun List<TrackDTO>.toTrackList() = mapNotNull { it.toTrack() }
fun TrackDTO.toTrack(): Track? {
    return Track(
        trackId = trackId ?: return null,
        trackName = trackName ?: return null,
        artistName = artistName ?: return null,
        trackTime = trackTime?.toLong()?.convertMS() ?: "",
        pictureURL = pictureURL ?: "",
        collectionName = collectionName ?: "",
        releaseDate = releaseDate ?: "",
        primaryGenreName = primaryGenreName ?: "",
        country = country ?: "",
        previewUrl = previewUrl ?: ""
    )
}