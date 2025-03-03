package com.terabyte.lessonnotes.activity

import android.content.Intent
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
import com.terabyte.lessonnotes.config.ABOUT_APP
import com.terabyte.lessonnotes.config.APP_VERSION
import com.terabyte.lessonnotes.config.CONTACTS_EMAIL
import com.terabyte.lessonnotes.viewmodel.SettingsViewModel

class SettingsActivity : ComponentActivity() {
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            Scaffold { paddingVals ->
                Main(viewModel, paddingVals)
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
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            startTermsActivity()
                        }
                )
                Text(
                    "Settings",
                    fontSize = 18.sp,
                    color = Color.Black,
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
            ) {
                Text(
                    "App version: $APP_VERSION",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Text(
                "About app:",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 10.dp, top = 20.dp)
            )
            Text(
                text = ABOUT_APP,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            Text(
                "Contacts:",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp)
            )
            Text(
                CONTACTS_EMAIL,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }

    private fun startTermsActivity() {
        startActivity(Intent(this, TermsActivity::class.java))
    }
}

