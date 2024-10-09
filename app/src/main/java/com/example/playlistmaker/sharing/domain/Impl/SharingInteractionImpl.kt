package com.example.playlistmaker.sharing.domain.Impl

import android.content.Context
import android.content.res.Resources
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.domain.SharingInteraction

class SharingInteractionImpl(
    private val intentNavigator: IntentNavigation
): SharingInteraction {
    private val context: Context = Creator.provideContext()

    override fun shareLink() {
        intentNavigator.shareLink(getShareAppLink())
    }



    override fun sendEmail() {

    }

    override fun openAgreement() {

    }

    private fun getShareAppLink(): String {
        return context.getString(R.string.android_developer_course_link)
    }
}