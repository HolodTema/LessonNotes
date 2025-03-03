package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.terabyte.lessonnotes.viewmodel.SubjectInfoViewModel

class SubjectInfoActivity : ComponentActivity() {
    private lateinit var viewModel: SubjectInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SubjectInfoViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM) && intent.extras!!.containsKey(
                INTENT_KEY_SUBJECT)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
                viewModel.subject = intent.extras!!.getSerializable(INTENT_KEY_SUBJECT, Subject::class.java)!!
            }
            else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM) as Term
                viewModel.subject = intent.extras!!.getSerializable(INTENT_KEY_SUBJECT) as Subject
            }
            viewModel.onExtrasGot()
        }

        enableEdgeToEdge()
        setContent {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButtonAddTask()
                }
            ) { paddingVals ->
                Main(viewModel, paddingVals)
            }
            if (viewModel.stateShowDeleteDialog.value) {
                DialogConfirmDelete(viewModel)
            }
        }
    }

    @Composable
    fun Main(viewModel: SubjectInfoViewModel, paddingVals: PaddingValues) {
        val tabHeaders = listOf(
            viewModel.textIncomplete,
            viewModel.textComplete
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingVals)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            )
            {
                Icon(
                    painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            backToTermInfoActivity()
                        }
                )
                Text(
                    text = viewModel.subject.name,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                )
                Icon(
                    painterResource(R.drawable.ic_delete),
                    contentDescription = "",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            viewModel.stateShowDeleteDialog.value = true
                        }
                )
            }
            TabRow(
                selectedTabIndex = viewModel.stateTabIndex.value
            ) {
                tabHeaders.forEachIndexed { i, tabHeader ->
                    Tab(
                        selected = i == viewModel.stateTabIndex.value,
                        onClick = {
                            viewModel.stateTabIndex.value = i
                        },
                        text = {
                            Text(tabHeader)
                        }
                    )
                }
            }
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val (x, y) = dragAmount
                            if (x > 30) {
                                viewModel.stateTabIndex.value = 0
                            }
                            if (x < -30) {
                                viewModel.stateTabIndex.value = 1
                            }
                        }
                    }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (viewModel.stateTabIndex.value == 0) {
                        itemsIndexed(viewModel.stateIncompleteTasks.value) { i, task ->
                            TaskListItem(task)
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                        }
                    }
                    if (viewModel.stateTabIndex.value == 1) {
                        itemsIndexed(viewModel.stateCompleteTasks.value) { i, task ->
                            TaskListItem(task)
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TaskListItem(task: Task) {
        val painter = when(task.importance) {
            1 -> {
                R.drawable.importance1
            }
            2 -> {
                R.drawable.importance2
            }
            3 -> {
                R.drawable.importance3
            }
            4 -> {
                R.drawable.importance4
            }
            5 -> {
                R.drawable.importance5
            }
            else -> {
                R.drawable.importance1
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Image(
                painterResource(painter),
                contentDescription = "",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 10.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = task.name,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = task.date,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = task.description,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Icon(
                    painter = if(task.completed) {
                        painterResource(R.drawable.ic_complete)
                    }
                    else {
                        painterResource(R.drawable.ic_incomplete)
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .padding(start = 10.dp)
                        .clickable {
                            if (task.completed) {
                                makeTaskIncomplete(task)
                            } else {
                                makeTaskComplete(task)
                            }
                        }
                )
            }
        }
    }

    @Composable
    fun FloatingActionButtonAddTask() {
        FloatingActionButton(
            onClick = {
                startCreateTaskActivity()
            }
        ) {
            Icon(
                painterResource(R.drawable.ic_add),
                contentDescription = "",
            )
        }
    }

    @Composable
    fun DialogConfirmDelete(viewModel: SubjectInfoViewModel) {
        AlertDialog(
            onDismissRequest = {
                viewModel.stateShowDeleteDialog.value = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        deleteSubject()
                        viewModel.stateShowDeleteDialog.value = false
                    }
                ) {
                    Text(viewModel.textDelete)
                }
            },
            title = {
                Text(viewModel.textDialogDeleteTitle)
            },
            text = {
                Text(viewModel.textDialogDeleteDesc)
            }
        )
    }

    private fun deleteSubject() {
        (application as MyApplication).roomManager.deleteSubjectWithChildren(viewModel.subject)
        backToTermInfoActivity()
    }

    private fun backToTermInfoActivity() {
        val intent = Intent(this, TermInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }

    private fun makeTaskIncomplete(task: Task) {
        viewModel.makeTaskIncomplete(task)
    }

    private fun makeTaskComplete(task: Task) {
        viewModel.makeTaskComplete(task)
    }

    private fun startCreateTaskActivity() {
        val intent = Intent(this, CreateTaskActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
            .putExtra(INTENT_KEY_SUBJECT, viewModel.subject)
        startActivity(intent)
    }
}

