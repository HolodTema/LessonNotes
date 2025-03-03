package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    val textAboutApp = application.resources.getString(R.string.settings_about_app)
    val textAppVersion = application.resources.getString(R.string.settings_app_version)
    val textContactsEmail = application.resources.getString(R.string.settings_contacts_email)

}