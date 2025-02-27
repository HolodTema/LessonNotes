package com.terabyte.lessonnotes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.viewmodel.SettingsViewModel

class SettingsActivity : ComponentActivity() {
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        enableEdgeToEdge()
        setContent {

        }
    }
}

