package com.example.playlistmaker.media.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.example.playlistmaker.media.data.temporary.PlaylistModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import android.widget.Toast as Toast

class CreatePlaylistFragment : Fragment() {

    private val viewModel: CreatePlaylistViewModel by viewModel()

    private var _binding: FragmentCreatePlaylistBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentCreatePlaylist must not be null")

    private var inputPlaylistName: String = ""
    private var inputPlaylistDescription: String = ""

    private var filePath: File? = null
    private var fileUri: Uri? = null
    private var coverUri: File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.loadImage.drawable != null || inputPlaylistName.isNotBlank() || inputPlaylistDescription.isNotBlank()) {
                showExitDialog()
            }
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    fileUri = uri
                    binding.loadImage.setImageURI(uri)
                    loadTemporaryImage(uri)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.playlist_image_successfully_loaded), Toast.LENGTH_SHORT
                    ).show()
                } else {

                }
            }

        binding.backArrow.setOnClickListener {
            if (binding.loadImage.drawable != null || inputPlaylistName.isNotBlank() || inputPlaylistDescription.isNotBlank()) {
                showExitDialog()
            } else {
            findNavController().popBackStack()
            }
        }

        binding.loadImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.textInputPlaylistEditText.doAfterTextChanged {
            inputPlaylistName = it.toString()

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

        binding.textInputDescriptionEditText.doAfterTextChanged {
            inputPlaylistDescription = it.toString()
        }

        binding.btnCreatePlaylist.setOnClickListener {
            fileUri?.let { saveImageToPrivateStorage(it) }
            val newPlaylist = PlaylistModel(
                playlistId = 0,
                playListName = inputPlaylistName,
                playListDescription = inputPlaylistDescription,
                coverUri = coverUri?.path ?: "",
                playlistTrackAmount = 0
            )
            viewModel.createPlaylist(newPlaylist)
            Toast.makeText(context,
                getString(R.string.Playlist_is_created, newPlaylist.playListName), Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }


    }

    private fun showExitDialog() {

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(getString(R.string.playlist_finish_creation))
        alertDialogBuilder.setMessage(getString(R.string.playlist_finish_message))

        alertDialogBuilder.setPositiveButton(getString(R.string.playlist_finish)) { dialog, with ->
            findNavController().popBackStack()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.playlist_finish_cancel)) { dialog, with ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun loadTemporaryImage(uri: Uri) {
        filePath = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "PlaylistAlbum"
        )
        if (filePath?.exists() == false) {
            filePath?.mkdirs()
        } else {
            binding.loadIcon.isVisible = false
        }
    }

    private fun saveImageToPrivateStorage(uri: Uri) {

        val coverName = UUID.randomUUID().toString()

        coverUri = File(filePath, coverName)
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(coverUri)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
    }


    companion object {

        fun newInstance() = CreatePlaylistFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}