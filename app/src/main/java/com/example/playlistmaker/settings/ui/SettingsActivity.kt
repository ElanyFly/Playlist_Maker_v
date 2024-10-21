package com.example.playlistmaker.settings.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()

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

        binding.settingsThemeSwitch.isChecked = viewModel.getThemeState()

        binding.settingsThemeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            viewModel.switchTheme(isChecked)
        }

        binding.settingsBackButton.setOnClickListener {
            finish()
        }

        binding.settingsShareButton.setOnClickListener {
            viewModel.shareLink()
        }

        binding.settingsHelpdeskButton.setOnClickListener {
            viewModel.sendEmail()
        }

        binding.settingsUserAgreementButton.setOnClickListener {
            viewModel.openAgreement()
        }
    }
}
