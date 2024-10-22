package com.example.playlistmaker.sharing.data.impl

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.domain.SharingResources

class SharingResourcesImpl(private val context: Context): SharingResources {
    override val courseLink: String by lazy { context.getString(R.string.android_developer_course_link) }
    override val helpdeskEmail: String by lazy { context.getString(R.string.helpdesk_email) }
    override val helpdeskMailHeader: String by lazy { context.getString(R.string.helpdesk_mail_header) }
    override val helpdeskMailMessage: String by lazy { context.getString(R.string.helpdesk_mail_message) }
    override val settingsAgreementLink: String by lazy { context.getString(R.string.settings_agreement_link) }
}