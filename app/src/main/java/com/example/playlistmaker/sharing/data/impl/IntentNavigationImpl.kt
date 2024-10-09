package com.example.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.playlistmaker.sharing.data.IntentNavigation

class IntentNavigationImpl(
    private val context: Context
): IntentNavigation {
    override fun shareLink(url: String) {
        val sendIntent = Intent(
            Intent.ACTION_SEND
        ).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(
            sendIntent, null
        )
        context.startActivity(shareIntent)
    }

    override fun sendEmail() {

    }

    override fun openAgreement() {

    }
}