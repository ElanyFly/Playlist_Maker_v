package com.example.playlistmaker.media.presentation.favourite_tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.audio_player.presentation.AudioPlayerFragment
import com.example.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.presentation.track_adapter.TrackAdapter
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.navigateToDestination
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteTracksFragment : Fragment() {

    private val viewModel: FavoriteTracksFragmentViewModel by viewModel()

    private var _binding: FragmentFavoriteTracksBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentFavoriteTracks must not be null")

    private var moveJob: Job? = null

    private val trackAdapter: TrackAdapter = TrackAdapter { track ->
        if (moveJob != null && moveJob?.isActive == true) {
            return@TrackAdapter
        }
        moveJob = lifecycleScope.launch {
            AudioPlayerFragment.newInstance(track)
            navigateToDestination(R.id.audioPlayerFragment2)

            delay(Constants.CLICK_DEBOUNCE_DELAY)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getContent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = trackAdapter

        viewModel.getContent()

        viewModel.state.observe(viewLifecycleOwner) {
            showContent(it)
        }
    }

    private fun showContent(state: FavoriteTracksFragmentState) {
        when(state) {
            is FavoriteTracksFragmentState.Content -> showTrackList(
                trackList = state.tracks
            )
            is FavoriteTracksFragmentState.Empty -> showEmptyMessage()
        }
    }

    private fun showEmptyMessage() {
        binding.mediaEmptyFavList.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun showTrackList(trackList: List<Track>) {
        binding.mediaEmptyFavList.isVisible = false
        binding.recyclerView.isVisible = true
        trackAdapter.updateTrackList(trackList)
    }

    companion object {
        fun newInstance() = FavoriteTracksFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}