package com.example.playlistmaker.settings.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    private val settingsActivityViewModel: SettingsActivityViewModel by viewModels()

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

        binding.settingsThemeSwitch.isChecked = settingsActivityViewModel.getThemeState()

        binding.settingsThemeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            settingsActivityViewModel.switchTheme(isChecked)
            App.switchTheme(isChecked)
        }

        binding.settingsBackButton.setOnClickListener {
            finish()
        }

        binding.settingsShareButton.setOnClickListener {
            settingsActivityViewModel.shareLink()
        }

        binding.settingsHelpdeskButton.setOnClickListener {
            settingsActivityViewModel.sendEmail()
        }

        binding.settingsUserAgreementButton.setOnClickListener {
            settingsActivityViewModel.openAgreement()
        }
    }

}
