package com.terabyte.lessonnotes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.viewmodel.CreateTaskViewModel

class CreateTaskActivity : ComponentActivity() {
    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateTaskViewModel::class.java]

        enableEdgeToEdge()
        setContent {

        }
    }
}

