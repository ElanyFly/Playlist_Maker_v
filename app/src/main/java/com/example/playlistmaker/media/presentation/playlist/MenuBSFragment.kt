package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMenuBSBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.navigateToDestination
import com.example.playlistmaker.utils.serialize
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuBSFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMenuBSBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentMenuBS must not be null")

    private val viewModel: MenuBSViewModel by viewModel()

    private lateinit var currentPlaylist: PlaylistWithTracksModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
        currentPlaylist = arguments?.getString(PLAYLIST_KEY)
            ?.deserialize<PlaylistWithTracksModel>() ?: run {
            onDestroy()
            return
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBSBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPlaylistData()

        binding.sharePlaylist.setOnClickListener {
            dismiss()
            if (currentPlaylist.playlistTracks.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.playlist_nothing_to_share),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.sharePlaylist(currentPlaylist)
            }
        }

        binding.deletePlaylist.setOnClickListener {
            showDeletePlaylistDialog(currentPlaylist)
        }

        binding.editPlaylistInfo.setOnClickListener {
            val bundle = Bundle().apply {
                putString("playlist", currentPlaylist.serialize())
            }
            navigateToDestination(R.id.createPlaylistFragment, bundle)
            dismiss()
        }

    }

    private fun setPlaylistData() {
        val trackSize: Int = currentPlaylist.playlistTracks.size
        val pluralText = resources.getQuantityString(
            R.plurals.track_count,
            trackSize,
            trackSize
        )
        with(binding) {
            getCover()
            playlistName.text = currentPlaylist.playListName
            playlistTrackCount.text = pluralText
        }

    }

    private fun getCover() {
        val cover = currentPlaylist.coverUri
        Glide.with(this)
            .load(cover)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .into(binding.playlistCover)
    }

    private fun showDeletePlaylistDialog(playlist: PlaylistWithTracksModel) {

        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        alertDialogBuilder.setTitle(getString(R.string.menu_playlist_delete_header))
        alertDialogBuilder.setMessage(getString(R.string.menu_playlist_delete_message))

        alertDialogBuilder.setPositiveButton(getString(R.string.menu_playlist_delete_yes)) { dialog, with ->
            viewModel.deletePlaylist(playlist)
            dismiss()
            findNavController().popBackStack()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.menu_playlist_delete_no)) { dialog, with ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }


    companion object {

        private const val PLAYLIST_KEY = "playlist_info"

        fun newInstance(playlistInfo: PlaylistWithTracksModel) = MenuBSFragment().apply {
            arguments = Bundle().apply {
                putString(PLAYLIST_KEY, playlistInfo.serialize())
            }
        }

    }
}