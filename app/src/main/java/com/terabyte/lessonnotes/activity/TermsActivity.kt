package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.config.INTENT_KEY_UPDATE_REQUIRED
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.ui.theme.LessonNotesTheme
import com.terabyte.lessonnotes.viewmodel.TermsViewModel

class TermsActivity : ComponentActivity() {
    private lateinit var viewModel: TermsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TermsViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_UPDATE_REQUIRED)) {
            viewModel.updateTermsList()
        }

        enableEdgeToEdge()
        setContent {
            LessonNotesTheme {
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
    }

    @Composable
    fun Main(paddingVals: PaddingValues, viewModel: TermsViewModel) {
        Column(
            modifier = Modifier
                .padding(paddingVals)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(colorScheme.primary)
                    .padding(vertical = 15.dp, horizontal = 10.dp)
            ) {
                Text(
                    "${viewModel.textAmountTerms} ${viewModel.termsList.value.size}",
                    style = typography.titleMedium,
                    color = colorScheme.onPrimary
                )
                Icon(
                    painterResource(R.drawable.ic_settings),
                    "",
                    tint = colorScheme.onPrimary,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
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
                .background(colorScheme.surface)
                .padding(10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    startTermInfoActivity(term)
                }
        ) {
            Text(
                "${viewModel.textTerm} ${term.number}",
                style = typography.titleLarge   ,
                color = colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = term.period,
                style = typography.bodyMedium,
                color = colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
            )
            Text(
                text = term.description,
                style = typography.bodyMedium,
                color = colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
            )
        }
    }

    @Composable
    fun ButtonCreateTerm(viewModel: TermsViewModel) {
        FloatingActionButton(
            onClick = {
            startActivity(Intent(this@TermsActivity, CreateTermActivity::class.java))
            },
            containerColor = colorScheme.secondary
        ) {
            Icon(
                painterResource(R.drawable.ic_add),
                "",
                tint = colorScheme.onSecondary,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
        }
    }

    private fun startTermInfoActivity(term: Term) {
        val intent = Intent(this, TermInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, term)
        startActivity(intent)
    }
}

