package com.example.playlistmaker.media.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistFragment : Fragment() {

    private val viewModel: PlaylistFragmentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PlaylistFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}