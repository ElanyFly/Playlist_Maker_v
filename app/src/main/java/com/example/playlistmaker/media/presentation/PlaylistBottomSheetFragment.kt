package com.example.playlistmaker.media.presentation

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.playlistmaker.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlaylistBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.playlist_bottom_sheet, container, false)
        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val peekHeight = (screenHeight * 0.7).toInt()
view.minimumHeight = peekHeight
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val behavior = dialog.
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return 
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = dialog?.findViewById<LinearLayout>(R.id.bottomSheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

//        setupBottomSheetHeight()
    }

    private fun setupBottomSheetHeight() {

        val bottomSheet = dialog?.findViewById<LinearLayout>(R.id.bottomSheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val peekHeight = (screenHeight * 0.7).toInt()
        this.view
        behavior.peekHeight = peekHeight
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}