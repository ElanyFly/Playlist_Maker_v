package com.example.playlistmaker.media.presentation.playlist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.audio_player.presentation.AudioPlayerFragment
import com.example.playlistmaker.databinding.FragmentTrackShowBsBinding
import com.example.playlistmaker.media.presentation.playlist_adapter.TrackShowBsAdapter
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.navigateToDestination
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrackShowBsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTrackShowBsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentTrackShowBsBinding must not be null")

    private var trackList: List<Track> = emptyList()
    private var moveJob: Job? = null

    private val trackAdapter: TrackShowBsAdapter = TrackShowBsAdapter() { track ->

        if (moveJob != null && moveJob?.isActive == true) {
            return@TrackShowBsAdapter
        }
        moveJob = lifecycleScope.launch {
//            viewModel.makeAction(SearchAction.AddTrackToHistoryList(track))
            AudioPlayerFragment.newInstance(track)
            navigateToDestination(R.id.audioPlayerFragment)
            delay(Constants.CLICK_DEBOUNCE_DELAY)
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackShowBsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        isCancelable = false
        dialog.window?.setDimAmount(0.2f)

        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottomSheetTrackShow)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.skipCollapsed = true
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomSheet = dialog?.findViewById<LinearLayout>(R.id.bottomSheetTrackShow)
//        val behavior = BottomSheetBehavior.from(bottomSheet!!)
//        behavior.skipCollapsed = true
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//        behavior.isHideable = false


        trackList = _trackListTemp ?: run {
            view.findNavController().popBackStack()
            return
        }


    }

    companion object {
        const val TAG = "BottomSheetDialogFragmentTrackShow"
        private var _trackListTemp: List<Track>? = null

        fun newInstance(trackList: List<Track>) = TrackShowBsFragment().apply {
            _trackListTemp = trackList
        }

    }


}