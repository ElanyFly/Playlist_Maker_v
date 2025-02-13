package com.example.playlistmaker.media.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMenuBSBinding
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.utils.deserialize
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

//        dialog?.window?.setNavigationBarContrastEnforced(false)

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