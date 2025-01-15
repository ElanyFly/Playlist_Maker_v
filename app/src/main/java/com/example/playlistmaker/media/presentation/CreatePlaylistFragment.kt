package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding

class CreatePlaylistFragment: Fragment() {

    private var _binding: FragmentCreatePlaylistBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentCreatePlaylist must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }




    companion object {

        fun newInstance() = CreatePlaylistFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}