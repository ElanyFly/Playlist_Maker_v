package com.example.playlistmaker.media.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatePlaylistViewModel(): ViewModel() {


    fun makeAction(action: CreatePlaylistAction) {
        when(action) {
            is CreatePlaylistAction.AddNewPlaylistToDb -> handleAddNewPlaylistToDb(action)
            CreatePlaylistAction.LoadImage -> handleLoadImage()
        }
    }

    private fun handleLoadImage() {


    }

    private fun handleAddNewPlaylistToDb(action: CreatePlaylistAction.AddNewPlaylistToDb) {


    }
}