package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.room.entity.Truancy
import com.terabyte.lessonnotes.viewmodel.TruanciesViewModel

class TruanciesActivity : ComponentActivity() {
    private lateinit var viewModel: TruanciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TruanciesViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
            }
            else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM) as Term
            }
            viewModel.onTermGot()
        }

        enableEdgeToEdge()
        setContent {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButtonCreateTruancy()
                }
            ) { paddingVals ->
                Main(viewModel, paddingVals)
            }
        }
    }

    @Composable
    fun Main(viewModel: TruanciesViewModel, paddingVals: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(paddingVals)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clickable {
                            backToTermInfoActivity()
                        }
                )
                Text(
                    "${viewModel.stateTruancies.value.size} truancies",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                items(viewModel.stateTruancies.value) { truancy ->
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }

    @Composable
    fun FloatingActionButtonCreateTruancy() {
        FloatingActionButton(
            onClick = {
                startCreateTruancyActivity()
            }
        ) {
            Icon(
                painterResource(R.drawable.ic_add),
                contentDescription = ""
            )
        }
    }

    @Composable
    fun TruancyListItem(truancy: Truancy) {

    }

    private fun startCreateTruancyActivity() {
        val intent = Intent(this, CreateTruancyActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }

    private fun backToTermInfoActivity() {
        val intent = Intent(this, TermInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }
}
