package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter() : RecyclerView.Adapter<TrackViewHolder>() {

    private var trackList: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view as ViewGroup)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            HistoryStore.addTrackToList(trackList[position])
        }
    }

    fun getTrackList(): List<Track> {
        return trackList
    }

    fun updateTrackList(searchResult: List<Track>) {
        trackList = searchResult
//        val mutableList = trackList.toMutableList()
//        mutableList.addAll(searchResult)
//        trackList = mutableList.toList()
        notifyDataSetChanged()
    }

    fun clearTrackList() {
        val mutableList = trackList.toMutableList()
        mutableList.clear()
        this.trackList = mutableList.toList()
        notifyDataSetChanged()
    }


}