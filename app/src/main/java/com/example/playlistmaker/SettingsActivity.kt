package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<FrameLayout>(R.id.settings_back_button)
        backButton.setOnClickListener {
            finish()
        }

        val shareButton = findViewById<LinearLayout>(R.id.settings_share_button)
//        shareButton.setOnClickListener {
//            val sendIntent = Intent(
//                Intent.ACTION_SEND
//            ).apply {
//                putExtra(
//                    Intent.EXTRA_TEXT,
//                    getString(R.string.android_developer_course_link)
//                )
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(
//                sendIntent, null
//            )
//            startActivity(shareIntent)
//        }

        shareButton.setOnClickListener{
            shareLink(getString(R.string.android_developer_course_link))
        }


    }

    private fun Context.shareLink(url: String) {
        val sendIntent = Intent(
            Intent.ACTION_SEND
        ).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(
            sendIntent, null
        )
        startActivity(shareIntent)
    }

}
