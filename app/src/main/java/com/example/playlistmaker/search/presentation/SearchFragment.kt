package com.example.playlistmaker.search.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.R
import com.example.playlistmaker.audio_player.presentation.AudioPlayerActivity
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.search.presentation.track_adapter.TrackAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment: Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentSearch must not be null")

    private var savedText = ""

    private var moveJob: Job? = null
    private var searchJob: Job? = null

    private val trackAdapter: TrackAdapter = TrackAdapter() { track ->

//        moveJob?.cancel()
//        moveJob = lifecycleScope.launch {
//            delay(CLICK_DEBOUNCE_DELAY)
//            viewModel.makeAction(SearchAction.AddTrackToHistoryList(track))
//            AudioPlayerActivity.showActivity(requireContext(), track)
//            if (binding.inputText.hasFocus() && binding.inputText.text.isEmpty()) {
//                showHistory(true)
//            }
//        }

        if (moveJob != null && moveJob?.isActive == true) {
            return@TrackAdapter
        }
        moveJob = lifecycleScope.launch {
            viewModel.makeAction(SearchAction.AddTrackToHistoryList(track))
            AudioPlayerActivity.showActivity(requireContext(), track)
            if (binding.inputText.hasFocus() && binding.inputText.text.isEmpty()) {
                showHistory(true)
            }
            delay(CLICK_DEBOUNCE_DELAY)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.makeAction(SearchAction.RestoreHistoryCache)

        binding.clearIcon.setOnClickListener {
            binding.inputText.setText("")
            hideKeyboard(binding.inputText)

            viewModel.makeAction(SearchAction.ClearSearchQuery)
        }

        binding.btnClearHistory.setOnClickListener {
            viewModel.makeAction(SearchAction.ClearTrackHistory)
        }

        val textWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                savedText = s.toString()
                binding.clearIcon.isVisible = savedText.isNotEmpty()

                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(INPUT_DELAY)
                    getTracks()
                }


            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        binding.inputText.addTextChangedListener(textWatcher)

        binding.recyclerView.adapter = trackAdapter

        binding.refreshButton.setOnClickListener {
            getTracks(isRefresh = true)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            trackAdapter.updateTrackList(state.trackList)
            showProgressBar(state.isLoading)
            showErrorMessage(
                isShowNothingFound = state.isNothingFound,
                isShowNetworkError = state.isNetworkError
            )
            showHistory(state.isHistoryShown)
        }
    }

    private fun getTracks(
        v: TextView = binding.inputText,
        isRefresh: Boolean = false
    ) {
        val query = v.text.toString()
        viewModel.makeAction(
            action = SearchAction.SearchTrack(
                inputQuery = query,
                isRefreshed = isRefresh
            )
        )
    }

    private fun showHistory(isShown: Boolean) {
        binding.historyHeader.isVisible = isShown
        binding.btnClearHistory.isVisible = isShown
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_TEXT_KEY, savedText)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val text = savedInstanceState?.getString(INPUT_TEXT_KEY) ?: ""
        savedText = text
        binding.inputText.setText(text)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showErrorMessage(
        isShowNothingFound: Boolean = false,
        isShowNetworkError: Boolean = false
    ) {
        binding.nothingFoundMessage.isVisible = isShowNothingFound
        binding.noInternetMessage.isVisible = isShowNetworkError
    }

    private fun showProgressBar(
        isShown: Boolean = false
    ) {
        binding.progressBar.isVisible = isShown
    }


    companion object {
        private const val INPUT_TEXT_KEY = "INPUT_TEXT"
        private const val INPUT_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}