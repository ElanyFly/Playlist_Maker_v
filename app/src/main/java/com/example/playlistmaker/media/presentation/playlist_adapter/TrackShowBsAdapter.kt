package com.example.playlistmaker.media.presentation.playlist_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.presentation.track_adapter.TrackViewHolder

class TrackShowBsAdapter(
    private val onClick: (Track) -> Unit,
    private val onLongClick: (Track) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var trackList: List<Track> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layputInspector = LayoutInflater.from(parent.context)
        return TrackViewHolder(TrackViewBinding.inflate(layputInspector, parent,false))
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(trackList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClick.invoke(trackList[position])
            true
        }
    }

    fun updateTrackList(trackList: List<Track>) {
        this.trackList = trackList
        notifyDataSetChanged()
    }
}