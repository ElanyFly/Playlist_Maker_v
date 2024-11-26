package com.example.playlistmaker.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: Fragment() {

    private val viewModel: SettingsViewModel by viewModel()

    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentSettingsBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsThemeSwitch.isChecked = viewModel.getThemeState()

        binding.settingsThemeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            viewModel.switchTheme(isChecked)
        }

        binding.settingsBackButton.setOnClickListener {
//            finish()
            TODO()
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