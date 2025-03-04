package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.ui.theme.LessonNotesTheme
import com.terabyte.lessonnotes.viewmodel.SettingsViewModel

class SettingsActivity : ComponentActivity() {
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            LessonNotesTheme {
                Scaffold { paddingVals ->
                    Main(viewModel, paddingVals)
                }
            }
        }
    }

    @Composable
    fun Main(viewModel: SettingsViewModel, paddingVals: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(paddingVals)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(colorScheme.primary)
                    .padding(vertical = 15.dp, horizontal = 10.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    tint = colorScheme.onPrimary,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            startTermsActivity()
                        }
                )
                Text(
                    "Settings",
                    style = typography.titleMedium,
                    color = colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorScheme.surface)
                    .padding(10.dp)
            ) {
                Text(
                    "App version: ${viewModel.textAppVersion}",
                    style = typography.titleMedium,
                    color = colorScheme.secondary
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorScheme.surface)
                    .padding(10.dp)
            ) {
                Text(
                    "About app:",
                    color = colorScheme.secondary,
                    style = typography.titleMedium,
                )
                Text(
                    text = viewModel.textAboutApp,
                    color = colorScheme.secondary,
                    style = typography.bodyMedium,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, end = 10.dp, start = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorScheme.surface)
                    .padding(10.dp)
            ) {
                Text(
                    "Contacts:",
                    color = colorScheme.secondary,
                    style = typography.titleMedium
                )
                Text(
                    viewModel.textContactsEmail,
                    fontSize = 16.sp,
                    color = colorScheme.secondary,
                    style = typography.bodyMedium
                )
            }

        }
    }

    private fun startTermsActivity() {
        startActivity(Intent(this, TermsActivity::class.java))
    }
}

