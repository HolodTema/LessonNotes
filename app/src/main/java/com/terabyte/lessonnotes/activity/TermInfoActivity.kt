package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.tooling.preview.Preview
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
import com.terabyte.lessonnotes.util.ColorHelper
import com.terabyte.lessonnotes.viewmodel.TermInfoViewModel

class TermInfoActivity : ComponentActivity() {
    private lateinit var viewModel: TermInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TermInfoViewModel::class.java]

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_TERM)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewModel.term =
                    intent.extras!!.getSerializable(INTENT_KEY_TERM, Term::class.java)!!
                viewModel.onTermSet()
            } else {
                viewModel.term = intent.extras!!.getSerializable(INTENT_KEY_TERM)!! as Term
                viewModel.onTermSet()
            }
        }

        enableEdgeToEdge()
        setContent {
            Scaffold { paddingVals ->
                Main(viewModel, paddingVals)
                if (viewModel.stateDeleteConfirmDialog.value) {
                    DialogConfirmDelete()
                }
            }
        }
    }

    @Composable
    fun Main(viewModel: TermInfoViewModel, paddingVals: PaddingValues) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(paddingVals)
                .verticalScroll(scrollState)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
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
                            backToTermsActivity()
                        }
                )
                Text(
                    text = "Term ${viewModel.term.number}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            viewModel.stateDeleteConfirmDialog.value = true
                        }
                )
            }
            Text(
                text = viewModel.term.period,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
            Text(
                text = viewModel.term.description,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Urgent tasks",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    Column {
                        viewModel.stateUrgentTasks.value.forEachIndexed { i, pair ->
                            UrgentTaskListItem(i, pair.first, pair.second)
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(350.dp)
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Subjects",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, end = 10.dp)
                                .weight(1f)
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "",
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .clickable {
                                    startCreateSubjectActivity()
                                }
                        )
                    }

                    LazyColumn {
                        itemsIndexed(viewModel.stateSubjects.value) { i, subject ->
                            SubjectListItem(subject)
                            if (i != viewModel.stateSubjects.value.size-1) {
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        startTruanciesActivity()
                    },
                    enabled = viewModel.stateSubjects.value.isNotEmpty()
                ) {
                    Text("View truancies")
                }
            }

        }
    }

    @Composable
    fun UrgentTaskListItem(index: Int, task: Task, subject: Subject) {
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = (index+1).toString(),
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 10.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Icon(
                        painterResource(R.drawable.ic_subject_marker),
                        contentDescription = "",
                        tint = ColorHelper.getColorByIndex(subject.colorType)
                    )
                    Text(
                        subject.name,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
            Image(
                painter = painterResource(painter),
                contentDescription = "",
                modifier = Modifier
                    .width(60.dp)
                    .height(50.dp)
            )
        }
    }

    @Composable
    fun SubjectListItem(subject: Subject) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    startSubjectInfoActivity(subject)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_subject_marker),
                contentDescription = "",
                tint = ColorHelper.getColorByIndex(subject.colorType),
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)
                    .padding(end = 10.dp)
            )
            Text(
                text = subject.name,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }

    @Composable
    fun DialogConfirmDelete() {
        AlertDialog(
            onDismissRequest = {
                viewModel.stateDeleteConfirmDialog.value = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        deleteTerm()
                    }
                ) {
                    Text("Delete term")
                }
            },
            title = {
                Text("Are you sure you want to delete the term?")
            },
            text = {
                Text("If you delete the term, you will also delete subjects, tasks and truancies list of this term.")
            }
        )
    }

    private fun backToTermsActivity() {
        startActivity(Intent(this, TermsActivity::class.java))
    }

    private fun deleteTerm() {
        (application as MyApplication).roomManager.deleteTermWithChildren(viewModel.term)
        backToTermsActivity()
    }

    private fun startCreateSubjectActivity() {
        val intent = Intent(this, CreateSubjectActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }

    private fun startSubjectInfoActivity(subject: Subject) {
        val intent = Intent(this, SubjectInfoActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        intent.putExtra(INTENT_KEY_SUBJECT, subject)
        startActivity(intent)
    }

    private fun startTruanciesActivity() {
        val intent = Intent(this, TruanciesActivity::class.java)
        intent.putExtra(INTENT_KEY_TERM, viewModel.term)
        startActivity(intent)
    }
}



