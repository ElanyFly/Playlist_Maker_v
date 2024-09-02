package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import com.example.playlistmaker.utils.convertMS
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize

class AudioplayerActivity : AppCompatActivity() {

    private lateinit var track: Track

    private var _binding: ActivityAudioplayerBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityAudioBinding must not be null")

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


        binding.backArrow.setOnClickListener {
            finish()
        }

    }

    private fun getDataToView(track: Track) {
        with(binding) {
            trackName.text = track.trackName
            groupName.text = track.artistName
            audioTrackTime.text = track.trackTime.toLong().convertMS()
            audioYear.text = track.releaseDate?.substringBefore("-") ?: ""
            audioGenre.text = track.primaryGenreName
            audioCountry.text = track.country

            trackTimeInProgress.text = track.trackTime.toLong().convertMS()

            if (track.collectionName.isNullOrBlank()){
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

    companion object {
        private const val TRACK_ID = "track_id"

        fun showActivity(context: Context, track: Track) {
            val trackString = track.serialize()
            val playerIntent = Intent(context, AudioplayerActivity::class.java).apply {

                putExtra(TRACK_ID, trackString)
            }
            context.startActivity(playerIntent)
        }
    }
}
