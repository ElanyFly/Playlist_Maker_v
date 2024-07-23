package com.example.playlistmaker


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)) {
    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val trackCover: ImageView = itemView.findViewById(R.id.trackCover)


    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = model.trackTime
        val coverUrl: String = model.artworkUrl100

        Glide.with(itemView.context)
            .load(coverUrl)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
            .into(trackCover)


    }

}