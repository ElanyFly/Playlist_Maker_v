package com.example.playlistmaker.sharing.domain.Impl

import android.content.res.Resources
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.IntentNavigation
import com.example.playlistmaker.sharing.domain.SharingInteraction

class SharingInteractionImpl(
    private val intentNavigator: IntentNavigation
): SharingInteraction {
    override fun shareLink() {
        intentNavigator.shareLink(getShareAppLink())
    }



    override fun sendEmail() {

    }

    override fun openAgreement() {

    }

    private fun getShareAppLink(): String {
        val appLink: String = Resources.getSystem().getString(R.string.android_developer_course_link)
        return appLink
    }
}