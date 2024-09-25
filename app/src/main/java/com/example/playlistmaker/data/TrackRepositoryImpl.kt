package com.example.playlistmaker.data

import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.data.network.call
import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.data.mappers.toTrackList

class TrackRepositoryImpl(private val apiService: TrackAPIService) : TrackRepository {

    override fun searchTracks(inputQuery: String): List<Track> {
        val response = apiService.searchTracks(inputQuery).call()

        return when(response) {
            is Response.onError -> emptyList()
            is Response.onSuccess -> response.data.results.toTrackList()
        }

    }
}