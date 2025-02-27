package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.media.presentation.playlist_adapter.GridItemDecoration
import com.example.playlistmaker.media.presentation.playlist_adapter.PlaylistAdapter
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.dpToPx
import com.example.playlistmaker.utils.navigateToDestination
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentPlaylist must not be null")

    private val viewModel: PlaylistFragmentViewModel by viewModel()

    private var moveJob: Job? = null
    private val playlistAdapter: PlaylistAdapter = PlaylistAdapter { playlistModel ->
        if (moveJob != null && moveJob?.isActive == true) {
            return@PlaylistAdapter
        }
        moveJob = lifecycleScope.launch {
            PlaylistInfoFragment.newInstance(playlistModel.playlistId)
            navigateToDestination(R.id.playlistInfoFragment2)
            delay(Constants.CLICK_DEBOUNCE_DELAY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllPlaylists()
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewPlaylist.setOnClickListener { view ->
            navigateToDestination(R.id.createPlaylistFragment)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.addItemDecoration(GridItemDecoration(
            spacingInner = 4.dpToPx(),
            spacingBottom = 16.dpToPx()
        ))

        binding.recyclerView.adapter = playlistAdapter

        viewModel.state.observe(viewLifecycleOwner) { playlistState ->
            when(playlistState) {
                PlaylistState.Empty -> setPlaylist()
                is PlaylistState.ShowContent -> setPlaylist(playlists = playlistState.playlists)
            }
        }

    }

    private fun setPlaylist(playlists: List<PlaylistWithTracksModel> = emptyList()) {
        binding.recyclerView.isVisible = playlists.isNotEmpty()
        showEmptyPlaylistMessage(playlists.isEmpty())
        playlistAdapter.updatePlayList(playlists)
    }

    private fun showEmptyPlaylistMessage(isShown: Boolean) {
        binding.mediaEmptyPlaylistsImg.isVisible = isShown
        binding.mediaEmptyPlaylistsText.isVisible = isShown
    }

    companion object {
        fun newInstance() = PlaylistFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}