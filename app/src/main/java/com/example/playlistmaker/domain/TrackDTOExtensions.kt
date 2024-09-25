package com.example.playlistmaker.domain

import com.example.playlistmaker.data.dto.TrackDTO
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.convertMS

fun List<TrackDTO>.toTrackList() = map { it.toTrack() }
fun TrackDTO.toTrack() = Track(
    trackId = trackId,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime.toLong().convertMS(),
    pictureURL = pictureURL,
    collectionName = collectionName,
    releaseDate = releaseDate,
    primaryGenreName = primaryGenreName,
    country = country,
    previewUrl = previewUrl
)