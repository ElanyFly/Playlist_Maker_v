package com.example.playlistmaker.ui.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.ui.audio_player.AudioplayerActivity
import com.example.playlistmaker.domain.HistoryStore
import com.example.playlistmaker.R
import com.example.playlistmaker.data.network.TrackAPIService
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.data.dto.TrackResponse
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private val searchActivityViewModel: SearchActivityViewModel by viewModels()

    private var _binding: ActivitySearchBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for SearchActivityBinding must not be null")

    private val retrofit: Retrofit by lazy { getClient(BASE_URL) }
    private val iTunesService by lazy { retrofit.create(TrackAPIService::class.java) }

    private var savedText = ""
    private var previousQuery = ""

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { getTracks() }

    private val trackAdapter: TrackAdapter = TrackAdapter() { track ->
        if (!isClickAllowed) return@TrackAdapter

        isClickAllowed = false
        handler.postDelayed({isClickAllowed = true}, CLICK_DEBOUNCE_DELAY)

        HistoryStore.addTrackToList(track)
        AudioplayerActivity.showActivity(this, track)
        if (binding.inputText.hasFocus() && binding.inputText.text.isEmpty()) {
            showHistory()
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

//        val historyList = HistoryStore.getHistoryList()
//        if (historyList.isNotEmpty()) {
//            showHistory(historyList)
//        }

        searchActivityViewModel.makeAction(SearchAction.RestoreHistoryCache)

        binding.clearIcon.setOnClickListener {
            binding.inputText.setText("")
            hideKeyboard(binding.inputText)
            clearTrackList()
//            showHistory()

            searchActivityViewModel.makeAction(SearchAction.ClearSearchQuery)
        }

        binding.searchBackButton.setOnClickListener {
            finish()
        }

        binding.btnClearHistory.setOnClickListener {
            searchActivityViewModel.makeAction(SearchAction.ClearTrackHistory)
//            HistoryStore.clearHistoryList()
            hideHistory()
        }

        val textWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                savedText = s.toString()
                binding.clearIcon.isVisible = savedText.isNotEmpty()
                if (binding.inputText.hasFocus() && s?.isEmpty() == true) {
                    showHistory()
                } else {
                    hideHistory()
                }

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

        lifecycleScope.launch {
            searchActivityViewModel.state.collect { state ->
                trackAdapter.updateTrackList(state.trackList)
                showProgressBar(state.isLoading)
                showErrorMessage(
                    isShowNothingFound = state.isNothingFound,
                    isShowNetworkError = state.isNetworkError
                )
            }
        }

    }

    private fun getTracks(
        actionId: Int = EditorInfo.IME_ACTION_DONE,
        v: TextView = binding.inputText,
        isRefresh: Boolean = false
    ): Boolean {

        val query = v.text.toString()
        searchActivityViewModel.makeAction(action = SearchAction.SearchTrack(inputQuery = query, isRefreshed = isRefresh))
        return true
        if (previousQuery == query && !isRefresh) {
            return true
        }
        previousQuery = query

        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            val trackData = iTunesService.searchTracks(query)
            if (query.isNotEmpty()) {

                hideHistory()
                showErrorMessage()
                clearTrackList()
                showProgressBar(true)

                trackData.clone().enqueue(object : Callback<TrackResponse> {
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        showProgressBar(false)
                        if (response.code() == 200) {
                            val searchResult = response.body()?.results

                            if (searchResult?.isNotEmpty() == true) {
//                                trackAdapter.updateTrackList(searchResult)
                            }
                            if (trackAdapter.getTrackList().isEmpty()) {
                                showErrorMessage(isShowNothingFound = true)
                            } else {
                                showErrorMessage()
                            }
                        } else {
                            showErrorMessage(isShowNetworkError = true)
                        }
                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        showProgressBar(false)
                        showErrorMessage(isShowNetworkError = true)
                    }

                })
            }
            true
        } else {
            false
        }
    }

    private fun clearTrackList() {
        trackAdapter.clearTrackList()
        showErrorMessage()
    }

    private fun showHistory(historyList: List<Track> = HistoryStore.getHistoryList()) {
//        trackAdapter.updateTrackList(historyList)
        if (historyList.isNotEmpty()) {
            binding.historyHeader.isVisible = true
            binding.btnClearHistory.isVisible = true
        }

    }

    private fun hideHistory() {
//        trackAdapter.updateTrackList(emptyList())
        binding.historyHeader.isVisible = false
        binding.btnClearHistory.isVisible = false
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

    private fun getClient(baseURL: String = BASE_URL): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }

        val newRetrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        return newRetrofit
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
        const val INPUT_TEXT_KEY = "INPUT_TEXT"
        const val BASE_URL = "https://itunes.apple.com"
        private const val INPUT_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }

}