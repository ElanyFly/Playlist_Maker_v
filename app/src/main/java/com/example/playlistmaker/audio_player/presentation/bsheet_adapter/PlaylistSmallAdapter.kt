package com.example.playlistmaker.audio_player.presentation.bsheet_adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistSmallViewBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel

class PlaylistSmallAdapter(
    private val onClick: (PlaylistWithTracksModel) -> Unit
) : RecyclerView.Adapter<PlaylistSmallViewHolder>() {

    private var playlist: List<PlaylistWithTracksModel> = emptyList()

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

    fun updatePlayList(playlists: List<PlaylistWithTracksModel>) {
        playlist = playlists
        notifyDataSetChanged()
    }
}

class PlaylistSmallViewHolder(
    private val binding: PlaylistSmallViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlaylistWithTracksModel) {
        with(binding) {
            val trackSize: Int = model.playlistTracks.size
            val pluralText = itemView.resources.getQuantityString(
                R.plurals.track_count,
                trackSize,
                trackSize
            )

            playlistName.text = model.playListName
            playlistTrackCount.text = pluralText
        }

        val coverUri: String = model.coverUri
        val test = Drawable.createFromPath(coverUri)
        Glide.with(itemView.context)
            .load(test)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
            .into(binding.playlistCover)

    }

}
