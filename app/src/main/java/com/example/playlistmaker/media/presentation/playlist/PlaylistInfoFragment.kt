package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.audio_player.presentation.AudioPlayerFragment
import com.example.playlistmaker.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.media.presentation.playlist_adapter.TrackShowBsAdapter
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.navigateToDestination
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistInfoFragment : Fragment() {

    private val viewModel: PlaylistInfoViewModel by viewModel()

    private var _binding: FragmentPlaylistInfoBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentPlaylistInfo must not be null")

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>


    private var playlistId: Int? = null
    private var trackList: List<Track> = emptyList()

    private var moveJob: Job? = null

    private val trackAdapter: TrackShowBsAdapter = TrackShowBsAdapter() { track ->

        if (moveJob != null && moveJob?.isActive == true) {
            return@TrackShowBsAdapter
        }
        moveJob = lifecycleScope.launch {

            AudioPlayerFragment.newInstance(track)
            navigateToDestination(R.id.audioPlayerFragment)
            delay(Constants.CLICK_DEBOUNCE_DELAY)
//            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlistId = _playlistId ?: kotlin.run {
            view.findNavController().popBackStack()
            return
        }

        val bottomSheet = view.findViewById<LinearLayout>(R.id.bottomSheetTrackShow)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
        val minHeightBs = resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTrackSHow)
        recyclerView.adapter = trackAdapter

        viewModel.getPlaylistWithTracks(playlistId!!)
        viewModel.playlistWithTracks.observe(viewLifecycleOwner) { playlistWithTracks ->
            setPlaylistInfo(playlistWithTracks)
            trackList = playlistWithTracks.playlistTracks
            trackAdapter.updateTrackList(playlistWithTracks.playlistTracks)

            if (trackList.isNotEmpty()) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetBehavior.isHideable = false
                bottomSheet.minimumHeight = minHeightBs
                bottomSheetBehavior.peekHeight = minHeightBs

            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                bottomSheet.isVisible = false
            }

        }

        binding.backArrow.setOnClickListener {
            view.findNavController().popBackStack()

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
            totalTrackTime.text = totalTrackTime(playlistWithTracks)
            trackCount.text = pluralText
            getCover(playlistWithTracks)
        }
    }

    private fun totalTrackTime(playlistWithTracks: PlaylistWithTracksModel): String {
        val time = playlistWithTracks.playlistTracks.mapNotNull {
            it.trackTime
        }.sum()
        val timeString = SimpleDateFormat("mm", Locale.getDefault()).format(time)
        val pluralMinutes = resources.getQuantityString(
            R.plurals.minutes,
            timeString.toInt(),
            timeString.toInt()
        )
        return pluralMinutes
    }

    private fun getCover(playlistWithTracks: PlaylistWithTracksModel) {
        val cover = playlistWithTracks.coverUri
        Glide.with(this)
            .load(cover)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .into(binding.coverUri)
    }


    companion object {
        private var _playlistId: Int? = null

        fun newInstance(playlistId: Int) {
            _playlistId = playlistId
        }
    }
}