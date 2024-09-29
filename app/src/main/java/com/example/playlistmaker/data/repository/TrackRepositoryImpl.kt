package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.mappers.toTrackList
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.data.network.call
import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.models.Response
import com.example.playlistmaker.domain.models.Tracks

class TrackRepositoryImpl(private val apiService: TrackAPIService) : TrackRepository {

    override fun searchTracks(inputQuery: String): Tracks {

        val response = apiService.searchTracks(inputQuery).call()

        return when(response) {
            is Response.Error -> Tracks(
                isError = true
            )
            is Response.Success -> Tracks(
                trackList = response.data.results.toTrackList()
            )
        }

    }
}