package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.util.ColorHelper
import com.terabyte.lessonnotes.util.DateHelper
import com.terabyte.lessonnotes.viewmodel.CreateTruancyViewModel

class CreateTruancyActivity : ComponentActivity() {
    private lateinit var viewModel: CreateTruancyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateTruancyViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term =
                    intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
            } else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM) as Term
            }
            viewModel.onTermGot()
        }

        enableEdgeToEdge()
        setContent {
            Scaffold { paddingVals ->
                Main(viewModel, paddingVals)
            }
            if (viewModel.stateShowDialogChangeSubject.value) {
                DialogChangeSubject(viewModel)
            }
            if (viewModel.stateShowDateDialog.value) {
                DialogDatePicker()
            }
        }
    }

    @Composable
    fun Main(viewModel: CreateTruancyViewModel, paddingVals: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingVals)
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
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            backToTruanciesActivity()
                        }
                )
                Text(
                    "Create new truancy",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                if (viewModel.stateChosenSubject.value != null) {
                    Icon(
                        painterResource(R.drawable.ic_subject_marker),
                        contentDescription = "",
                        tint = ColorHelper.getColorByIndex(viewModel.stateChosenSubject.value!!.colorType),
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                    Text(
                        viewModel.stateChosenSubject.value!!.name,
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp)
                    )
                    Button(
                        onClick = {
                            viewModel.stateShowDialogChangeSubject.value = true
                        }
                    ) {
                        Text("Change subject")
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Text(
                    viewModel.stateTruancyDate.value,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.stateShowDateDialog.value = true
                    }
                ) {
                    Text("Change date")
                }
            }
            TextField(
                value = viewModel.stateTruancyReason.value,
                placeholder = {
                    Text("The reason of truancy")
                },
                onValueChange = {
                    viewModel.stateTruancyReason.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        createTruancy()
                    }
                ) {
                    Text("Create")
                }
            }
        }
    }

    @Composable
    fun DialogChangeSubject(viewModel: CreateTruancyViewModel) {
        Dialog (
            onDismissRequest = {
                viewModel.stateShowDialogChangeSubject.value = false
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
                    .wrapContentWidth()
                    .height(400.dp)

            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp)
                ) {
                    Text(
                        "Choose subject of truancy",
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        itemsIndexed(viewModel.subjects) { i, subject ->
                            SubjectDialogListItem(subject)
                            if (i != viewModel.subjects.size-1) {
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = Color.Gray
                                )
                            }
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
                                viewModel.stateShowDialogChangeSubject.value = false
                            },
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SubjectDialogListItem(subject: Subject) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.
                fillMaxWidth()
        ) {
            Icon(
                painterResource(R.drawable.ic_subject_marker),
                contentDescription = "",
                tint = ColorHelper.getColorByIndex(subject.colorType),
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
            Text(
                subject.name,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            )
            RadioButton(
                onClick = {
                    viewModel.stateChosenSubject.value = subject
                },
                selected = subject.id == viewModel.stateChosenSubject.value!!.id
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DialogDatePicker() {
        val dateState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {
                viewModel.stateShowDateDialog.value =false
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (dateState.selectedDateMillis != null) {
                            val date = DateHelper.getDateFromMillis(dateState.selectedDateMillis!!)
                            viewModel.stateTruancyDate.value = date
                        }
                        viewModel.stateShowDateDialog.value =false
                    }
                ) {
                    Text("Done")
                }
            }
        ) {
            DatePicker(state = dateState)
        }
    }

    private fun backToTruanciesActivity() {
        val intent = Intent(this, TruanciesActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }

    private fun createTruancy() {
        viewModel.createTruancy()

        val intent = Intent(this, TruanciesActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }

}

