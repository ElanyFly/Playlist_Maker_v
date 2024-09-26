package com.example.playlistmaker.ui.audio_player

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.Constants
import com.example.playlistmaker.utils.convertMS
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize

class AudioPlayerActivity : AppCompatActivity() {

    private val viewModel: AudioPlayerActivityViewModel by viewModels()

    private lateinit var track: Track

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

        this.track = intent.getStringExtra(TRACK_ID)?.deserialize<Track>()
            ?: run {
                finish()
                return
            }
        getCover(track)
        getDataToView(track)

        preparePlayer(track)

        binding.btnPlay.setOnClickListener {
            playbackControl()
        }

        binding.backArrow.setOnClickListener {
            finish()
        }

    }

    override fun onStop() {
        super.onStop()
        pausePlayer()

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPLayer.release()
    }

    private fun getDataToView(track: Track) {
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

//            setOnCompletionListener {
//                binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
//                handler.removeCallbacks(timeRunnable)
//                binding.trackTimeInProgress.text = Constants.PLAYER_TIME_DEFAULT
//                playerState = StatePlayer.PREPARED
//            }

    }
//
//    private fun startPlayer() {
//        mediaPLayer.start()
//        binding.btnPlay.setImageResource(R.drawable.audio_pausebutton)
//        playerState = StatePlayer.PLAYING
//        getPositionDelay()
//    }

    private fun pausePlayer() {
        binding.btnPlay.setImageResource(R.drawable.audio_playbutton)
    }

    private fun playbackControl() {
        when (playerState) {
            StatePlayer.PLAYING -> pausePlayer()

            StatePlayer.PREPARED,
            StatePlayer.PAUSED -> startPlayer()

            StatePlayer.DEFAULT -> Unit
        }
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
