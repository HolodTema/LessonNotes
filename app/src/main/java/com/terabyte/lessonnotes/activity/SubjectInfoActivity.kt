package com.terabyte.lessonnotes.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.config.INTENT_KEY_SUBJECT
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.viewmodel.SubjectInfoViewModel

class SubjectInfoActivity : ComponentActivity() {
    private lateinit var viewModel: SubjectInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SubjectInfoViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM) && intent.extras!!.containsKey(
                INTENT_KEY_SUBJECT)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
                viewModel.subject = intent.extras!!.getSerializable(INTENT_KEY_SUBJECT, Subject::class.java)!!
            }
            else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM) as Term
                viewModel.subject = intent.extras!!.getSerializable(INTENT_KEY_SUBJECT) as Subject
            }
            viewModel.onExtrasGot()
        }

        enableEdgeToEdge()
        setContent {
            Scaffold(
                floatingActionButton = {

                }
            ) { paddingVals ->
                Main(viewModel, paddingVals)
            }
        }
    }

    @Composable
    fun Main(viewModel: SubjectInfoViewModel, paddingVals: PaddingValues) {
        
    }
}

