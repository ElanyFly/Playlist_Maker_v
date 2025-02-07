package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.databinding.FragmentTrackShowBsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TrackShowBsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTrackShowBsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentTrackShowBsBinding must not be null")

//    private var trackList: List<Track> = emptyList()
//    private var moveJob: Job? = null
//
//    private val trackAdapter: TrackShowBsAdapter = TrackShowBsAdapter() { track ->
//
//        if (moveJob != null && moveJob?.isActive == true) {
//            return@TrackShowBsAdapter
//        }
//        moveJob = lifecycleScope.launch {
//
//            AudioPlayerFragment.newInstance(track)
//            navigateToDestination(R.id.audioPlayerFragment)
//            delay(Constants.CLICK_DEBOUNCE_DELAY)
//            dismiss()
//        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTrackShowBsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        trackList = _trackListTemp ?: run {
//            view.findNavController().popBackStack()
//            return
//        }

//        binding.recyclerView.adapter = trackAdapter
//        trackAdapter.updateTrackList(trackList)


    }

//    companion object {
//        private var _trackListTemp: List<Track>? = null
//
//        fun newInstance(trackList: List<Track>) = TrackShowBsFragment().apply {
//            _trackListTemp = trackList
//        }
//
//    }


}