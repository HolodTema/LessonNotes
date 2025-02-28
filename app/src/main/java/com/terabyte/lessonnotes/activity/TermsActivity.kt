package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Term
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
                    ButtonCreateTerm(viewModel)
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
        Column(
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
                    "Amount of terms: ${viewModel.termsList.value.size}",
                    fontSize = 18.sp,
                    color = Color.Black
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
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),

            ) {
                items(viewModel.termsList.value) { term ->
                    TermCard(term)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun TermCard(term: Term) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .padding(10.dp)
                .clickable {
                    startTermInfoActivity(term)
                }
        ) {
            Text(
                "Term ${term.number}",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = term.period,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
            )
            Text(
                text = term.description,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
            )
        }
    }

    @Composable
    fun ButtonCreateTerm(viewModel: TermsViewModel) {
        FloatingActionButton(onClick = {
            startActivity(Intent(this@TermsActivity, CreateTermActivity::class.java))
        }) {
            Icon(
                painterResource(R.drawable.ic_add),
                ""
            )
        }
    }

    private fun startTermInfoActivity(term: Term) {
        val intent = Intent(this, TermInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, term)
        startActivity(intent)
    }
}

