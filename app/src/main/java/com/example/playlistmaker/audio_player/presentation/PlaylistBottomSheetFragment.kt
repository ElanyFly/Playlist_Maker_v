package com.example.playlistmaker.audio_player.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.R
import com.example.playlistmaker.audio_player.presentation.bsheet_adapter.PlaylistSmallAdapter
import com.example.playlistmaker.databinding.PlaylistBottomSheetBinding
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.navigateToDestination
import com.example.playlistmaker.utils.serialize
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: PlaylistBottomSheetBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for PlaylistBottomSheet must not be null")

    private val viewModel: PlaylistBottomSheetViewModel by viewModel()

    private lateinit var track: Track

    private var addTrackJob: Job? = null
    private val playlistAdapter: PlaylistSmallAdapter = PlaylistSmallAdapter { playlistModel ->
        if (addTrackJob != null && addTrackJob?.isActive == true) {
            return@PlaylistSmallAdapter
        }
        addTrackJob = lifecycleScope.launch {
            viewModel.addTrackToPlaylist(playlistModel, track) { isTrackAddedBefore ->
                val message = if (isTrackAddedBefore) {
                    getString(
                        R.string.playlist_track_already_exist,
                        playlistModel.playListName
                    )
                } else {
                    getString(
                        R.string.playlist_track_successfully_added,
                        playlistModel.playListName
                    )
                }
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            dismiss()
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
        arguments?.getString(TRACK_KEY)?.deserialize<Track>()?.let {
            track = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet = dialog?.findViewById<LinearLayout>(R.id.bottomSheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.recyclerView.adapter = playlistAdapter

        viewModel.getAllPlaylists()
        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            binding.recyclerView.isVisible = playlists.isNotEmpty()
            playlistAdapter.updatePlayList(playlists)
        }

        binding.btnNewPlaylist.setOnClickListener {
            navigateToDestination(R.id.createPlaylistFragment)
            dismiss()
        }

    }

    companion object {
        private const val TRACK_KEY = "key_track"
        fun newInstance(track: Track) = PlaylistBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(TRACK_KEY, track.serialize())
            }
        }
    }

}

