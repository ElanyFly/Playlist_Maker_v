package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Switch
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class SettingsActivity : AppCompatActivity() {

    private val themeSwitch by lazy { findViewById<Switch>(R.id.themeSwitch) }
    private val backButton by lazy { findViewById<FrameLayout>(R.id.settings_back_button) }
    private val shareButton by lazy { findViewById<LinearLayout>(R.id.settings_share_button) }
    private val helpdeskButton by lazy { findViewById<LinearLayout>(R.id.settings_helpdesk_button) }
    private val agreementButton by lazy { findViewById<LinearLayout>(R.id.settings_user_agreement_button) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        themeSwitch.isChecked = SharedPreferencesManager.getSwitchState()
        themeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            App.switchTheme(isChecked)
        }

        backButton.setOnClickListener {
            finish()
        }

        shareButton.setOnClickListener {
            shareLink(getString(R.string.android_developer_course_link))
        }

        helpdeskButton.setOnClickListener {
            sendEmail(
                arrayOf(getString(R.string.helpdesk_email)),
                getString(R.string.helpdesk_mail_header),
                getString(R.string.helpdesk_mail_message)
            )
        }

        agreementButton.setOnClickListener {
            openAgreement(getString(R.string.settings_agreement_link))
        }

    }

    private fun shareLink(url: String) {
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

    private fun sendEmail(email: Array<String>, header: String, message: String) {
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

    private fun openAgreement(url: String) {
        val showAgreement = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(showAgreement)
    }

}
