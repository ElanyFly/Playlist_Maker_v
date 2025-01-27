package com.example.playlistmaker.media.presentation

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.media.data.temporary.PlaylistInteractor
import com.example.playlistmaker.media.presentation.playlist_adapter.GridItemDecoration
import com.example.playlistmaker.media.presentation.playlist_adapter.PlaylistAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
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
            //move inside playlist
            //show message no playlist
            delay(CLICK_DEBOUNCE_DELAY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).toInt()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mediaBtnNewPlaylist.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_global_createPlaylistFragment2)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.addItemDecoration(GridItemDecoration(
            spacingInner = 4.dpToPx(),
            spacingBottom = 16.dpToPx()
        ))
        binding.recyclerView.adapter = playlistAdapter
        lifecycleScope.launch(Dispatchers.IO) {
            interactor.getAllPlaylists().collect { playlists ->
                withContext(Dispatchers.Main) {
                    binding.recyclerView.isVisible = playlists.isNotEmpty()
                    showEmptyPlaylistMessage(playlists.isEmpty())
                    playlistAdapter.updatePlayList(playlists)
                }
            }
        }



    }

    private fun showEmptyPlaylistMessage(isShown: Boolean) {
        binding.mediaEmptyPlaylistsImg.isVisible = isShown
        binding.mediaEmptyPlaylistsText.isVisible = isShown
    }


val interactor: PlaylistInteractor by inject()

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L

        fun newInstance() = PlaylistFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}