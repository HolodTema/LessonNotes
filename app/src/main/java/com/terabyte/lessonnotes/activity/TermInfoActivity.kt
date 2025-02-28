package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import com.terabyte.lessonnotes.config.INTENT_KEY_TERM
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term
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
        Column(
            modifier = Modifier
                .padding(paddingVals)
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
                        .height(250.dp)
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
                    LazyColumn {
                        itemsIndexed(viewModel.stateUrgentTasks.value) { i, task ->
                            UrgentTaskListItem(i, task)
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
                        itemsIndexed(viewModel.stateSubjects.value) { _, subject ->
                            SubjectListItem(subject)
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

                    },
                    modifier = Modifier

                ) {
                    Text("View truancies")
                }
            }

        }
    }

    @Composable
    fun UrgentTaskListItem(index: Int, task: Task) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = index.toString(),
                fontSize = 18.sp,
                color = Color.Black
            )
            Column(

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
            }
            Image(
                painter = painterResource(R.drawable.importance5),
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
            modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_subject_marker),
                contentDescription = "",
                tint = Color.Green,
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
        startActivity(Intent(this, CreateSubjectActivity::class.java))
    }
}



