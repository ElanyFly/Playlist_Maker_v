package com.example.playlistmaker.search.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.audio_player.ui.AudioPlayerActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.search.presentation.SearchAction
import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.search.ui.track_adapter.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: ActivitySearchBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for SearchActivityBinding must not be null")


    private var savedText = ""

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { getTracks() }

    private val trackAdapter: TrackAdapter = TrackAdapter() { track ->
        if (!isClickAllowed) return@TrackAdapter

        isClickAllowed = false
        handler.postDelayed({isClickAllowed = true}, CLICK_DEBOUNCE_DELAY)

        viewModel.makeAction(SearchAction.AddTrackToHistoryList(track))
        AudioPlayerActivity.showActivity(this, track)
        if (binding.inputText.hasFocus() && binding.inputText.text.isEmpty()) {
            showHistory(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.search) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        viewModel.makeAction(SearchAction.RestoreHistoryCache)

        binding.clearIcon.setOnClickListener {
            binding.inputText.setText("")
            hideKeyboard(binding.inputText)

            viewModel.makeAction(SearchAction.ClearSearchQuery)
        }

        binding.searchBackButton.setOnClickListener {
            finish()
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
                inputDebounce()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        binding.inputText.addTextChangedListener(textWatcher)

        binding.recyclerView.adapter = trackAdapter

        binding.refreshButton.setOnClickListener {
            getTracks(isRefresh = true)
        }

        viewModel.state.observe(this) { state ->
            trackAdapter.updateTrackList(state.trackList)
            showProgressBar(state.isLoading)
            showErrorMessage(
                isShowNothingFound = state.isNothingFound,
                isShowNetworkError = state.isNetworkError
            )
            showHistory(state.isHistoryShown)
        }

//        lifecycleScope.launch {
//            viewModel.state.collect { state ->
//                trackAdapter.updateTrackList(state.trackList)
//                showProgressBar(state.isLoading)
//                showErrorMessage(
//                    isShowNothingFound = state.isNothingFound,
//                    isShowNetworkError = state.isNetworkError
//                )
//                showHistory(state.isHistoryShown)
//            }
//        }

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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val text = savedInstanceState.getString(INPUT_TEXT_KEY) ?: ""
        savedText = text
        binding.inputText.setText(text)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showErrorMessage(
        isShowNothingFound: Boolean = false,
        isShowNetworkError: Boolean = false
    ) {
        binding.nothingFoundMessage.isVisible = isShowNothingFound
        binding.noInternetMessage.isVisible = isShowNetworkError
    }

    fun inputDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, INPUT_DELAY)
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