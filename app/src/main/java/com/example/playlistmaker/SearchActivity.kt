package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private val inputText by lazy { findViewById<EditText>(R.id.inputText) }
    private val clearButton by lazy { findViewById<ImageView>(R.id.clearIcon) }
    private val backButton by lazy { findViewById<FrameLayout>(R.id.search_back_button) }
    private val nothingFoundMessage by lazy { findViewById<LinearLayout>(R.id.nothingFoundMessage) }
    private val noInternetMessage by lazy { findViewById<LinearLayout>(R.id.noInternetMessage) }
    private val tracksRecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val refreshButton by lazy { findViewById<Button>(R.id.refreshButton) }

    private val historyHeader by lazy { findViewById<TextView>(R.id.historyHeader) }
    private val btnClearHistory by lazy { findViewById<Button>(R.id.btnClearHistory) }

    private val retrofit: Retrofit by lazy { getClient(BASE_URL) }
    private val iTunesService by lazy { retrofit.create(TrackAPIService::class.java) }

    private var savedText = ""
    private val trackAdapter: TrackAdapter = TrackAdapter() { track ->
        HistoryStore.addTrackToList(track)
        if (inputText.hasFocus() && inputText.text.isEmpty()) {
            showHistory()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val historyList = HistoryStore.getHistoryList()
        if (historyList.isNotEmpty()) {
            showHistory(historyList)
        }

        clearButton.setOnClickListener {
            inputText.setText("")
            hideKeyboard(inputText)
            clearTrackList()
            showHistory()

        }

        backButton.setOnClickListener {
            finish()
        }

        btnClearHistory.setOnClickListener {
            HistoryStore.clearHistoryList()
            hideHistory()
        }

        val textWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                savedText = s.toString()
                clearButton.isVisible = savedText.isNotEmpty()
                if (inputText.hasFocus() && s?.isEmpty() == true) {
                    showHistory()
                } else {
                    hideHistory()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        inputText.addTextChangedListener(textWatcher)

        tracksRecyclerView.adapter = trackAdapter

        inputText.setOnEditorActionListener { v, actionId, event ->
            hideHistory()
            getTracks(actionId, v)
        }

        refreshButton.setOnClickListener {
            getTracks()
        }


    }

    private fun getTracks(
        actionId: Int = EditorInfo.IME_ACTION_DONE,
        v: TextView = inputText
    ): Boolean {
        showErrorMessage()
        clearTrackList()
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            val query = v.text.toString()
            val trackData = iTunesService.searchTracks(query)
            if (query.isNotEmpty()) {
                trackData.clone().enqueue(object : Callback<TrackResponse> {
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        if (response.code() == 200) {
                            val searchResult = response.body()?.results

                            if (searchResult?.isNotEmpty() == true) {
                                trackAdapter.updateTrackList(searchResult)
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
        trackAdapter.updateTrackList(historyList)
        if (historyList.isNotEmpty()) {
            historyHeader.isVisible = true
            btnClearHistory.isVisible = true
        }

    }

    private fun hideHistory() {
        trackAdapter.updateTrackList(emptyList())
        historyHeader.isVisible = false
        btnClearHistory.isVisible = false
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_TEXT_KEY, savedText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val text = savedInstanceState.getString(INPUT_TEXT_KEY) ?: ""
        savedText = text
        inputText.setText(text)
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
        nothingFoundMessage.isVisible = isShowNothingFound
        noInternetMessage.isVisible = isShowNetworkError
    }

    companion object {
        const val INPUT_TEXT_KEY = "INPUT_TEXT"
        const val BASE_URL = "https://itunes.apple.com"
    }

}