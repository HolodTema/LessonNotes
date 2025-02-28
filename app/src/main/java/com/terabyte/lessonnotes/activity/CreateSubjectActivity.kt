package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.util.ColorHelper
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
            if (viewModel.stateShowDialogColor.value) {
                DialogChooseColor(viewModel)
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
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            TextField(
                value = viewModel.stateSubjectName.value,
                singleLine = true,
                onValueChange = {
                    viewModel.stateSubjectName.value = it
                },
                placeholder = {
                    Text("Subject name")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Subject color:",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Icon(
                    painterResource(R.drawable.ic_subject_marker),
                    contentDescription = "",
                    tint = ColorHelper.getColorByIndex(viewModel.stateColorIndex.value),
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .padding(horizontal = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.stateShowDialogColor.value = true
                    }
                ) {
                    Text("Change oclor")
                }
            }
        }
    }

    @Composable
    fun DialogChooseColor(viewModel: CreateSubjectViewModel) {
        val stateColorIndex = remember { mutableStateOf(viewModel.stateColorIndex.value) }

        Dialog(
            onDismissRequest = {
                viewModel.stateShowDialogColor.value = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Choose color of subject",
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        itemsIndexed(ColorHelper.getColors()) { i, color ->
                            Box(
                                modifier = if (i == stateColorIndex.value) {
                                    Modifier
                                        .wrapContentWidth()
                                        .width(60.dp)
                                        .height(60.dp)
                                        .padding(5.dp)
                                        .background(ColorHelper.getColors()[i])
                                        .border(5.dp, Color(0xFF000000))
                                } else {
                                    Modifier
                                        .wrapContentWidth()
                                        .width(60.dp)
                                        .height(60.dp)
                                        .padding(5.dp)
                                        .background(ColorHelper.getColors()[i])
                                        .border(1.dp, Color.Black)
                                        .clickable {
                                            stateColorIndex.value = i
                                        }
                                }
                            )
                            { }
                        }
                    }
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.stateColorIndex.value = stateColorIndex.value
                                viewModel.stateShowDialogColor.value = false
                            },
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }

    private fun backToTermInfoActivity() {
        val intent = Intent(this, TermInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }
}
