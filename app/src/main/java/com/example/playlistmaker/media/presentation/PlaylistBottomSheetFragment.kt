package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistBottomSheetBinding
import com.example.playlistmaker.media.data.temporary.PlaylistInteractor
import com.example.playlistmaker.media.presentation.playlist_adapter.PlaylistAdapter
import com.example.playlistmaker.media.presentation.playlist_adapter.PlaylistSmallAdapter
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: PlaylistBottomSheetBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for PlaylistBottomSheet must not be null")

    private val viewModel: PlaylistBottomSheetViewModel by viewModel()

    private lateinit var track: Track

    private var addTrackJob: Job? = null
    private val playlistAdapter: PlaylistSmallAdapter = PlaylistSmallAdapter  { playlistModel ->
        if (addTrackJob != null && addTrackJob?.isActive == true) {
            return@PlaylistSmallAdapter
        }
        addTrackJob = lifecycleScope.launch {
            viewModel.addTrackToPlaylist(playlistModel, track)
            //show message added to playlist
        }
    }

    val interactor: PlaylistInteractor by inject()

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
        lifecycleScope.launch(Dispatchers.IO) {
            interactor.getAllPlaylists().collect {
                withContext(Dispatchers.Main) {
                    binding.recyclerView.isVisible = it.isNotEmpty()
                    playlistAdapter.updatePlayList(it)
                }
            }
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

