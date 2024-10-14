package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.search.data.mappers.toTrackList
import com.example.playlistmaker.search.data.network.TrackAPIService
import com.example.playlistmaker.search.data.network.call
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.models.Response
import com.example.playlistmaker.search.domain.models.Tracks

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