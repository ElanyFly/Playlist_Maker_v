package com.example.playlistmaker.media.presentation.playlist_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistViewBinding
import com.example.playlistmaker.media.data.temporary.PlaylistModel

class PlaylistAdapter(
    private val onClick: (PlaylistModel) -> Unit
) : RecyclerView.Adapter<PlaylistViewHolder>() {

    private var playlist: List<PlaylistModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(PlaylistViewBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlist[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(playlist[position])
        }
    }
}

class PlaylistViewHolder(private val binding: PlaylistViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PlaylistModel) {
            with(binding) {
                playlistName.text = model.playListName
                playlistTrackCount.text = model.playlistTrackAmount.toString()
            }

            val coverUri: String = model.coverUri
            Glide.with(itemView.context)
                .load(coverUri)
                .placeholder(R.drawable.placeholder_45)
                .centerCrop()
                .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
                .into(binding.playlistCover)

        }

}
