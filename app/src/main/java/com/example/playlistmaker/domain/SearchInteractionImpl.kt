package com.example.playlistmaker.domain

import com.example.playlistmaker.data.dto.TrackResponse
import com.example.playlistmaker.data.mappers.toTrackList
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.domain.models.Track
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchInteractionImpl(
    private val itunesService: TrackAPIService
) : SearchInteraction {

    private var previousQuery = ""

    override fun searchTrack(
        query: String,
        isRefreshed: Boolean,
        resultLambda: (SearchResult) -> Unit
    ) {
        if (previousQuery == query && !isRefreshed) {
            return
        }
        previousQuery = query
        val trackData = itunesService.searchTracks(query)
        if (query.isNotEmpty()) {
            resultLambda(SearchResult.Loading)
            trackData.clone().enqueue(
                object : Callback<TrackResponse> {
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        if (response.code() == 200) {
                            val searchResult = response.body()?.results?.toTrackList()
                            if (searchResult == null) {
                                resultLambda(SearchResult.Error(isNetworkError = true))
                                return
                            }
                            if (searchResult.isNotEmpty()) {
                                resultLambda(SearchResult.Success(trackList = searchResult))
                                return
                            } else {
                                resultLambda(SearchResult.Error(isNothingFound = true))
                                return
                            }

                        } else {
                            resultLambda(SearchResult.Error(isNetworkError = true))
                            return
                        }

                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        resultLambda(SearchResult.Error(isNetworkError = true))
                    }

                }
            )
        }
    }

    override fun clearTrackHistory() {
        HistoryStore.clearHistoryList()
    }

    override fun addTrackToHistory(track: Track) {
        HistoryStore.addTrackToList(track)
    }

    override fun restoreHistoryCache(): List<Track> {
        return HistoryStore.getHistoryList()
    }

}
