package com.example.playlistmaker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.utils.Constants
import okhttp3.internal.readFieldOrNull

class TrackAdapter(
    private val track: List<Track>
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var historyList: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view as ViewGroup)
    }

    override fun getItemCount(): Int {
        return track.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track[position])
        holder.itemView.setOnClickListener {
            addTrackToList(track[position])
        }
    }

    fun getHistoryList(): List<Track> {
        return historyList
    }

    fun addTrackToList(track: Track) {
        val mutableHistoryList = historyList.toMutableList()
        checkHistoryMaxSize(mutableHistoryList, track)

        historyList = mutableHistoryList.toList()
        Log.i("addTrack", "historyList - $historyList")
//        notifyDataSetChanged()
    }

    private fun checkHistoryMaxSize(
        mutableHistoryList: MutableList<Track>,
        track: Track
    ) {
        if (mutableHistoryList.size == 9) {
            mutableHistoryList.removeLast()
            mutableHistoryList.add(0, track)
        } else {
            mutableHistoryList.add(0, track)
        }
    }

    fun checkTrackRepeat(track: Track) {}



}