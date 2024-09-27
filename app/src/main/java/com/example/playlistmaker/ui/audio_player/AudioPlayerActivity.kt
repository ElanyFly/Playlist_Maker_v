package com.example.playlistmaker.ui.audio_player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize
import kotlinx.coroutines.launch

class AudioPlayerActivity : AppCompatActivity() {

    private val viewModel: AudioPlayerActivityViewModel by viewModels()

    private var _binding: ActivityAudioplayerBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityAudioBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audioplayer)

        _binding = ActivityAudioplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.audioPlayerMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val track = intent.getStringExtra(TRACK_ID)?.deserialize<Track>()
            ?: run {
                finish()
                return
            }


        lifecycleScope.launch {
            viewModel.state.collect {state ->
                setDataToView(state.track)
                setPlayTime(state.playTime)
                when{
                    state.isPlaying -> startPlayer()
                    state.isPaused -> pausePlayer()
                    state.isFinished -> pausePlayer()
                }

            }
        }

        preparePlayer(track)

        binding.btnPlay.setOnClickListener {
            viewModel.makeAction(AudioPlayerAction.pressPlayBtn)
        }

        binding.backArrow.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        pausePlayer()
        viewModel.makeAction(AudioPlayerAction.pressPlayBtn)

    }

    private fun setDataToView(track: Track) {
        with(binding) {
            trackName.text = track.trackName
            groupName.text = track.artistName
            audioTrackTime.text = track.trackTime
            audioYear.text = track.releaseDate?.substringBefore("-") ?: ""
            audioGenre.text = track.primaryGenreName
            audioCountry.text = track.country

            if (track.collectionName.isNullOrBlank()) {
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
        private const val TRACK_ID = "track_id"

        fun showActivity(context: Context, track: Track) {
            val trackString = track.serialize()
            val playerIntent = Intent(context, AudioPlayerActivity::class.java).apply {

                putExtra(TRACK_ID, trackString)
            }
            context.startActivity(playerIntent)
        }
    }
}
