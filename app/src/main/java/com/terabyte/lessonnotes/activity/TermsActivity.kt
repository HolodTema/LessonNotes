package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.ui.theme.LessonNotesTheme
import com.terabyte.lessonnotes.viewmodel.TermsViewModel

class TermsActivity : ComponentActivity() {
    private lateinit var viewModel: TermsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TermsViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            Scaffold(
                floatingActionButton = {

                },
                modifier = Modifier
                    .fillMaxSize()
            ) { paddingVals ->
                Main(paddingVals, viewModel)
            }
        }
    }

    @Composable
    fun Main(paddingVals: PaddingValues, viewModel: TermsViewModel) {
        Box(
            modifier = Modifier
                .padding(paddingVals)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp, 5.dp)
            ) {
                Text(
                    "Amount of terms: ${viewModel.termsList.value.size}"
                )
                Icon(
                    painterResource(R.drawable.ic_settings),
                    "",
                    modifier = Modifier
                        .clickable {
                            startActivity(Intent(this@TermsActivity, SettingsActivity::class.java))
                        }
                )
            }
        }
    }

    @Composable
    fun ButtonCreateTerm(viewModel: TermsViewModel) {
        FloatingActionButton(onClick = {

        }) {
            Icon(
                painterResource(R.drawable.ic_settings),
                ""
            )
        }
    }
}

