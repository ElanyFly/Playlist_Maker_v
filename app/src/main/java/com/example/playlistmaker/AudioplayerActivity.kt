package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.utils.serialize

class AudioplayerActivity : AppCompatActivity() {


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


        binding.backArrow.setOnClickListener {
            finish()
        }

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