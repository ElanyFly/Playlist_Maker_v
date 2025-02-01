package com.example.playlistmaker.media.presentation.playlist_adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val spacingInner: Int, private val spacingBottom: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spacingInner
        outRect.right = spacingInner
        outRect.bottom = spacingBottom
    }
}