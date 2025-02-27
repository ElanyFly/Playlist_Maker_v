package com.example.playlistmaker.media.presentation.playlist

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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

class PlaylistInfoFragment : Fragment() {

    private val viewModel: PlaylistInfoViewModel by viewModel()

    private var _binding: FragmentPlaylistInfoBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentPlaylistInfo must not be null")

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var emptyMessage: TextView

    private var playlistId: Int? = null
    private var trackList: List<Track> = emptyList()

    private var moveJob: Job? = null
    private var menuBSFragment: MenuBSFragment? = null

    private val trackAdapter: TrackShowBsAdapter = TrackShowBsAdapter(
        onClick = { track ->

            if (moveJob != null && moveJob?.isActive == true) {
                return@TrackShowBsAdapter
            }
            moveJob = lifecycleScope.launch {

                AudioPlayerFragment.newInstance(track)
                navigateToDestination(R.id.audioPlayerFragment2)
                delay(Constants.CLICK_DEBOUNCE_DELAY)
            }
        },
        onLongClick = { track ->
            showDeleteTrackDialog(track)
        },
    )

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

        ViewCompat.setOnApplyWindowInsetsListener(binding.playlistInfo) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            (binding.backArrow.layoutParams as? MarginLayoutParams)?.topMargin =
                systemBars.top + requireContext().resources.getDimensionPixelSize(R.dimen.padding_14dp)
            insets
        }

        playlistId = _playlistId ?: kotlin.run {
            view.findNavController().popBackStack()
            return
        }

        val bottomSheet = view.findViewById<LinearLayout>(R.id.bottomSheetTrackShow)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)

        emptyMessage = view.findViewById(R.id.emptyListMessage)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTrackSHow)
        recyclerView.adapter = trackAdapter

        viewModel.getPlaylistWithTracks(playlistId!!)
        viewModel.playlistWithTracks.observe(viewLifecycleOwner) { playlistWithTracks ->
            setPlaylistInfo(playlistWithTracks)
            trackList = playlistWithTracks.playlistTracks
            trackAdapter.updateTrackList(playlistWithTracks.playlistTracks)
            bottomSheetExtraction(bottomSheet)
        }

        binding.backArrow.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.shareIcon.setOnClickListener {
            if (trackList.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.playlist_nothing_to_share),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.sharePlaylist()
            }
        }

        binding.tripleIcon.setOnClickListener {
            val playlist = viewModel.getCurrentPlaylist() ?: return@setOnClickListener
            MenuBSFragment.newInstance(
                playlistInfo = playlist,
                onDelete = {
                    showDeletePlaylistDialog(playlist)
                    menuBSFragment?.dismiss()
                }
            ).let {
                menuBSFragment = it
                it.show(parentFragmentManager, menuBSFragment?.tag)
            }
        }

    }

    private fun showDeletePlaylistDialog(playlist: PlaylistWithTracksModel) {

        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialogBuilder.setTitle(getString(R.string.menu_playlist_delete_header))
        alertDialogBuilder.setMessage(getString(R.string.menu_playlist_delete_message))

        alertDialogBuilder.setPositiveButton(getString(R.string.menu_playlist_delete_yes)) { dialog, with ->
            viewModel.deletePlaylist(playlist)
            findNavController().popBackStack()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.menu_playlist_delete_no)) { dialog, with ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun bottomSheetExtraction(bottomSheet: LinearLayout) {
        if (trackList.isNotEmpty()) {
            bottomSheetBehavior.peekHeight =
                resources.getDimensionPixelSize(R.dimen.playlist_bs_peek_height)
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            emptyMessage.isVisible = false
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.isHideable = false
            emptyMessage.isVisible = true
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
        val time = playlistWithTracks.playlistTracks.sumOf {
            it.trackTime
        }
        val timeString = time / 1000 / 60
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

    private fun showDeleteTrackDialog(track: Track) {

        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialogBuilder.setTitle(getString(R.string.playlist_info_delete_track_q))
        alertDialogBuilder.setMessage(getString(R.string.playlist_info_delete_message))

        alertDialogBuilder.setPositiveButton(getString(R.string.playlist_info_delete_agree)) { dialog, with ->
            viewModel.deleteTrackFromPlaylist(track)
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.playlist_finish_cancel)) { dialog, with ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }


    companion object {
        private var _playlistId: Int? = null

        fun newInstance(playlistId: Int) {
            _playlistId = playlistId
        }
    }

}