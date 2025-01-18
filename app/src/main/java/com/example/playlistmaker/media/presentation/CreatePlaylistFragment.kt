package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.example.playlistmaker.search.presentation.SearchAction
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePlaylistFragment: Fragment() {

    private val viewModel: CreatePlaylistViewModel by viewModel()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadImage.setOnClickListener {
            viewModel.makeAction()
        }

        binding.textInputPlaylistEditText.doAfterTextChanged {
            var textInput = it.toString()

            if (it?.isNotBlank() == true) {
                binding.btnCreatePlaylist.setBackgroundColor(
                    requireContext().getColor(R.color.tumbler_head)
                )
            } else {
                binding.btnCreatePlaylist.setBackgroundColor(
                    requireContext().getColor(R.color.grey_123)
                )
            }


        }
    }



    companion object {

        fun newInstance() = CreatePlaylistFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}