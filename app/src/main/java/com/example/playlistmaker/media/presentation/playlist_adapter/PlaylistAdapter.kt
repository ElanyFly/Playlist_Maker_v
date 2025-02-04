package com.example.playlistmaker.media.presentation.playlist_adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistViewBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel

class PlaylistAdapter(
    private val onClick: (PlaylistWithTracksModel) -> Unit
) : RecyclerView.Adapter<PlaylistViewHolder>() {

    private var playlist: List<PlaylistWithTracksModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(PlaylistViewBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlist[position], holder.itemView)
        holder.itemView.setOnClickListener {
            onClick.invoke(playlist[position])
        }
    }

    fun updatePlayList(playlists: List<PlaylistWithTracksModel>) {
        playlist = playlists
        notifyDataSetChanged()
    }
}

class PlaylistViewHolder(private val binding: PlaylistViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlaylistWithTracksModel, itemView: View) {

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
        Glide.with(this.itemView.context)
            .load(test)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .transform(RoundedCorners(this.itemView.context.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
            .into(binding.playlistCover)

    }

}
