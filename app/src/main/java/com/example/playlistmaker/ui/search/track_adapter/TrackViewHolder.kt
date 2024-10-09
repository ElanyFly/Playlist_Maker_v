package com.example.playlistmaker.ui.search.track_adapter


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.domain.models.Track

class TrackViewHolder(private val binding: TrackViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Track) {
        with(binding) {
            trackName.text = model.trackName
            artistName.text = model.artistName
            trackTime.text = model.trackTime
        }


        val coverUrl: String = model.pictureURL

        Glide.with(itemView.context)
            .load(coverUrl)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
            .into(binding.trackCover)


    }

}