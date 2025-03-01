package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.config.INTENT_KEY_SUBJECT
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.util.DateHelper
import com.terabyte.lessonnotes.viewmodel.CreateTaskViewModel

class CreateTaskActivity : ComponentActivity() {
    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateTaskViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM) && intent.extras!!.containsKey(
                INTENT_KEY_SUBJECT
            )
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term =
                    intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
                viewModel.subject =
                    intent.extras!!.getSerializable(INTENT_KEY_SUBJECT, Subject::class.java)!!
            } else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM) as Term
                viewModel.subject = intent.extras!!.getSerializable(INTENT_KEY_SUBJECT) as Subject
            }

        }
        enableEdgeToEdge()
        setContent {
            Scaffold(

            ) { paddingVals ->
                Main(viewModel, paddingVals)
            }
            if (viewModel.stateShowDateDialog.value) {
                DialogDatePicker()
            }
        }
    }

    @Composable
    fun Main(viewModel: CreateTaskViewModel, paddingVals: PaddingValues) {
        val importanceLevels = listOf(1, 2, 3, 4, 5)

        Column(
            modifier = Modifier
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
                        .width(40.dp)
                        .height(40.dp)
                        .clickable {
                            backToSubjectInfoActivity()
                        }
                )
                Text(
                    "Create new task",
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            TextField(
                value = viewModel.stateTaskName.value,
                onValueChange = {
                    viewModel.stateTaskName.value = it
                },
                placeholder = {
                    Text("Task name")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )

            TextField(
                value = viewModel.stateTaskDescription.value,
                onValueChange = {
                    viewModel.stateTaskDescription.value = it
                },
                placeholder = {
                    Text("Task description")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    viewModel.stateTaskDate.value,
                    color = Color.Black,
                    fontSize = 18.sp,
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
            Text(
                "Choose importance:",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                itemsIndexed(importanceLevels) { i, importanceLevel ->
                    ImportanceCard(importanceLevel)
                }
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        createTask()
                    },
                    enabled = viewModel.stateTaskName.value.isNotEmpty()
                ) {
                    Text("Create")
                }
            }
        }
    }

    @Composable
    fun ImportanceCard(importanceLevel: Int) {
        val texts = listOf(
            "",
            "Not important",
            "Little important",
            "Important",
            "Very important",
            "As soon as possible"
        )
        val painters = listOf(
            R.drawable.importance1,
            R.drawable.importance1,
            R.drawable.importance2,
            R.drawable.importance3,
            R.drawable.importance4,
            R.drawable.importance5,
        )

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = if (viewModel.stateTaskImportance.value == importanceLevel) Color.Gray else Color.White),
            modifier = Modifier
                .wrapContentWidth()
                .height(140.dp)
                .padding(10.dp)
                .clickable {
                    viewModel.stateTaskImportance.value = importanceLevel
                }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                Image(
                    painterResource(painters[importanceLevel]),
                    contentDescription = "",
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(bottom = 5.dp)
                )
                Text(
                    texts[importanceLevel],
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
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
                            viewModel.stateTaskDate.value = date
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

    private fun backToSubjectInfoActivity() {
        val intent = Intent(this, SubjectInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
            .putExtra(INTENT_KEY_SUBJECT, viewModel.subject)
        startActivity(intent)
    }

    private fun createTask() {
        val task = Task(
            0,
            viewModel.subject.id,
            viewModel.term.id,
            viewModel.stateTaskName.value,
            viewModel.stateTaskDescription.value,
            viewModel.stateTaskDate.value,
            viewModel.stateTaskImportance.value,
            false
        )

        (application as MyApplication).roomManager.insertTask(task)

        val intent = Intent(this, SubjectInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
            .putExtra(INTENT_KEY_SUBJECT, viewModel.subject)
        startActivity(intent)
    }
}

