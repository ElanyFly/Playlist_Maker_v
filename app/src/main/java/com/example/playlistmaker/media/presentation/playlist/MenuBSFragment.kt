package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMenuBSBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuBSFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMenuBSBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentMenuBS must not be null")

    private val viewModel: MenuBSViewModel by viewModel()

    private lateinit var currentPlaylist: PlaylistWithTracksModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
        currentPlaylist = arguments?.getString(PLAYLIST_KEY)
            ?.deserialize<PlaylistWithTracksModel>() ?: run {
            onDestroy()
            return
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBSBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPlaylistData()

    }

    private fun setPlaylistData() {
        val trackSize: Int = currentPlaylist.playlistTracks.size
        val pluralText = resources.getQuantityString(
            R.plurals.track_count,
            trackSize,
            trackSize
        )
        with(binding) {
            getCover()
            playlistName.text = currentPlaylist.playListName
            playlistTrackCount.text = pluralText
        }

    }

    private fun getCover() {
        val cover = currentPlaylist.coverUri
        Glide.with(this)
            .load(cover)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .into(binding.playlistCover)
    }


    companion object {

        private const val PLAYLIST_KEY = "playlist_info"

        fun newInstance(playlistInfo: PlaylistWithTracksModel) = MenuBSFragment().apply {
            arguments = Bundle().apply {
                putString(PLAYLIST_KEY, playlistInfo.serialize())
            }
        }

    }
}