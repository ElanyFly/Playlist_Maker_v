package com.example.playlistmaker.sharing.data.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.sharing.data.IntentNavigation

class IntentNavigationImpl(
): IntentNavigation {

    val context: Context = Creator.provideContext()
    override fun shareLink(url: String) {

        val sendIntent = Intent(
            Intent.ACTION_SEND
        ).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(
            sendIntent, null
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }

    override fun sendEmail() {

    }

    override fun openAgreement() {

    }
}