package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.net.Uri
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
        shareButton.setOnClickListener{
            shareLink(getString(R.string.android_developer_course_link))
        }

        val helpdeskButton = findViewById<LinearLayout>(R.id.settings_helpdesk_button)
        helpdeskButton.setOnClickListener{

            sendEmail(
                arrayOf(getString(R.string.helpdesk_email)),
                getString(R.string.helpdesk_mail_header),
                getString(R.string.helpdesk_mail_message))
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

    private fun Context.sendEmail(email: Array<String>, header: String, message: String) {
        val sendIntent = Intent(
            Intent.ACTION_SENDTO
        ).apply {
            setData(Uri.parse("mailto:"))
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_SUBJECT, header)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

}
