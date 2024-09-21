package com.example.playlistmaker.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.data.storage.SharedPreferencesManager
import com.example.playlistmaker.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    private var _binding: ActivitySettingsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for SettingsBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.settings) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.settingsThemeSwitch.isChecked = SharedPreferencesManager.instance.getSwitchState()
        binding.settingsThemeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            App.switchTheme(isChecked)
        }

        binding.settingsBackButton.setOnClickListener {
            finish()
        }

        binding.settingsShareButton.setOnClickListener {
            shareLink(getString(R.string.android_developer_course_link))
        }

        binding.settingsHelpdeskButton.setOnClickListener {
            sendEmail(
                arrayOf(getString(R.string.helpdesk_email)),
                getString(R.string.helpdesk_mail_header),
                getString(R.string.helpdesk_mail_message)
            )
        }

        binding.settingsUserAgreementButton.setOnClickListener {
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
