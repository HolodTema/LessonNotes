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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.viewmodel.CreateSubjectViewModel

class CreateSubjectActivity : ComponentActivity() {
    private lateinit var viewModel: CreateSubjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateSubjectViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term =
                    intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
            } else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM)!! as Term
            }
        }

        enableEdgeToEdge()
        setContent {
            Scaffold { paddingVals ->
                Main(viewModel, paddingVals)
            }
        }
    }

    @Composable
    fun Main(viewModel: CreateSubjectViewModel, paddingVals: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingVals)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            backToTermInfoActivity()
                        }
                )
                Text(
                    text = "Create new subject",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }

    private fun backToTermInfoActivity() {
        val intent = Intent(this, TermInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }
}
