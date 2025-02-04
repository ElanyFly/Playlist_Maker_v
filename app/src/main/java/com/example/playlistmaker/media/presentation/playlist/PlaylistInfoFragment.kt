package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistInfoFragment: Fragment() {

    private val viewModel: PlaylistInfoViewModel by viewModel()

    private var _binding: FragmentPlaylistInfoBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentPlaylistInfo must not be null")

    private var playlistId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlistId = _playlistId ?: kotlin.run {
            view.findNavController().popBackStack()
            return
        }

        viewModel.getPlaylistWithTracks(playlistId!!)
        viewModel.playlistWithTracks.observe(viewLifecycleOwner) { playlistWithTracks ->
            setPlaylistInfo(playlistWithTracks)
        }


    }

    private fun setPlaylistInfo(playlistWithTracks: PlaylistWithTracksModel) {
        val trackSize: Int = playlistWithTracks.playlistTracks.size
        val pluralText = resources.getQuantityString(
            R.plurals.track_count,
            trackSize,
            trackSize
        )
        with(binding) {
            playlistName.text = playlistWithTracks.playListName
            playlistDescription.text = playlistWithTracks.playListDescription
            totalTrackTime.text = "ddd"
            trackCount.text = pluralText
            getCover(playlistWithTracks)
        }


    }

    private fun getCover(playlistWithTracks: PlaylistWithTracksModel) {
        TODO("Not yet implemented")
    }


    companion object {
        private var _playlistId: Int? = null

        fun newInstance(playlistId: Int) {
            _playlistId = playlistId
        }
    }
}