package com.example.playlistmaker.media.presentation.playlist_adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistSmallViewBinding
import com.example.playlistmaker.databinding.PlaylistViewBinding
import com.example.playlistmaker.media.data.temporary.PlaylistWithTracksEntity

class PlaylistSmallAdapter(
    private val onClick: (PlaylistWithTracksEntity) -> Unit
) : RecyclerView.Adapter<PlaylistSmallViewHolder>() {

    private var playlist: List<PlaylistWithTracksEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSmallViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistSmallViewHolder(PlaylistSmallViewBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: PlaylistSmallViewHolder, position: Int) {
        holder.bind(playlist[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(playlist[position])
        }
    }

    fun updatePlayList(playlists: List<PlaylistWithTracksEntity>) {
        playlist = playlists
        notifyDataSetChanged()
    }
}

class PlaylistSmallViewHolder(
    private val binding: PlaylistSmallViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlaylistWithTracksEntity) {
        with(binding) {
            playlistName.text = model.playlistEntity.playListName
            playlistTrackCount.text =
                binding.root.context
                    .getString(R.string.playlist_track_count, model.tracks.size.toString())
        }

        val coverUri: String = model.playlistEntity.coverUri
        val test = Drawable.createFromPath(coverUri)
        Glide.with(itemView.context)
            .load(test)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
            .into(binding.playlistCover)

    }

}
