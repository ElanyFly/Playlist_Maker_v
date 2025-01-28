package com.example.playlistmaker.audio_player.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentAudioplayerBinding
import com.example.playlistmaker.media.presentation.PlaylistBottomSheetFragment
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.deserialize
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioPlayerFragment : Fragment() {

    private val viewModel: AudioPlayerViewModel by viewModel()

    private var _binding: FragmentAudioplayerBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityAudioBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAudioplayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.audioPlayerMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val track = arguments?.getString(TRACK_ID)?.deserialize<Track>()
            ?: run {
                view.findNavController().popBackStack()
                return
            }

        viewModel.playerState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe
            setDataToView(state.track)
            setPlayTime(state.playTime)
            when{
                state.isPlaying -> startPlayer()
                state.isPaused -> pausePlayer()
                state.isFinished -> pausePlayer()
            }
        }

        preparePlayer(track)

        binding.btnPlay.setOnClickListener {
            viewModel.makeAction(AudioPlayerAction.pressPlayBtn(false))
        }

        binding.backArrow.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.btnLike.setOnClickListener {
            viewModel.makeAction(AudioPlayerAction.pressLikeBtn(track))
        }

        binding.btnAdd.setOnClickListener {
            val bottomSheetFragment = PlaylistBottomSheetFragment.newInstance(track)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    override fun onStop() {
        super.onStop()
        pausePlayer()
        viewModel.makeAction(AudioPlayerAction.pressPlayBtn(true))

    }

    private fun setDataToView(track: Track) {
        with(binding) {
            btnLike.setImageResource(
                if (track.isFavorite) {
                    R.drawable.audio_clicked_like
                } else {
                    R.drawable.audio_likebutton
                }
            )

            trackName.text = track.trackName
            groupName.text = track.artistName
            audioTrackTime.text = track.trackTime
            audioYear.text = track.releaseDate.substringBefore("-") ?: ""
            audioGenre.text = track.primaryGenreName
            audioCountry.text = track.country

            if (track.collectionName.isBlank()) {
                groupAlbum.isVisible = false
            } else {
                groupAlbum.isVisible = true
                audioAlbumName.text = track.collectionName
            }
            getCover(track)
        }
    }

    private fun getCover(track: Track) {
        val biggerCover = track.pictureURL.replaceAfterLast('/', "512x512bb.jpg")
        Glide.with(this)
            .load(biggerCover)
            .placeholder(R.drawable.placeholder_45)
            .centerCrop()
            .transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.image_round_corners)))
            .into(binding.audioplayerCover)
    }

    private fun preparePlayer(track: Track) {
        viewModel.makeAction(AudioPlayerAction.prepareTrack(track))
    }

    private fun startPlayer() {
        binding.btnPlay.setImageResource(R.drawable.audio_pausebutton)
    }

    private fun pausePlayer() {
        binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
    }

    private fun setPlayTime(playTime: String) {
        binding.trackTimeInProgress.text = playTime
    }

    companion object {
        private const val TRACK_ID = "track"

//        fun showActivity(context: Context, track: Track) {
//            val trackString = track.serialize()
//            val playerIntent = Intent(context, AudioPlayerFragment::class.java).apply {
//
//                putExtra(TRACK_ID, trackString)
//            }
//            context.startActivity(playerIntent)
//        }
    }
}
