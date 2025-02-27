package com.terabyte.lessonnotes.activity

import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.viewmodel.CreateTermViewModel

class CreateTermActivity : ComponentActivity() {
    private lateinit var viewModel: CreateTermViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateTermViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            Scaffold { paddingVals ->
                Main(viewModel, paddingVals)
            }
        }
    }

    @Composable
    fun Main(viewModel: CreateTermViewModel, paddingVals: PaddingValues) {
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
                            startTermsActivity()
                        }
                )
                Text(
                    "Create new term",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            TextField(
                value = viewModel.stateTermNumber.value,
                label = {
                    Text("Term number")
                },
                singleLine = true,
                onValueChange = {
                    viewModel.stateTermNumber.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Starts at: ${viewModel.stateTermDateStart.value}",
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.stateShowDialogChangeStartDate.value = true
                    }
                ) {
                    Text("Change")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Ends at: ${viewModel.stateTermDateEnd.value}",
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.stateShowDialogChangeEndDate.value = true
                    }
                ) {
                    Text("Change")
                }
            }
            TextField(
                value = viewModel.stateTermDescription.value,
                label = {
                    Text("Term description")
                },
                onValueChange = {
                    viewModel.stateTermDescription.value = it
                },
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Button(
                    enabled = (viewModel.stateTermNumber.value.isNotEmpty() && viewModel.stateTermDateStart.value.isNotEmpty() && viewModel.stateTermDateEnd.value.isNotEmpty()),
                    onClick = {

                    },
                ) {
                    Text("Create")
                }
            }
        }

        if (viewModel.stateShowDialogChangeStartDate.value) {
            ChangeDateDialog(
                initYear = 2024,
                initMonthIndex = 0,
                dismissListener = {
                    viewModel.stateShowDialogChangeStartDate.value = false
                },
                applyListener = { year, monthIndex ->
                    viewModel.stateShowDialogChangeStartDate.value = false
                }
            )
        }

        if (viewModel.stateShowDialogChangeEndDate.value) {
            ChangeDateDialog(
                initYear = 2024,
                initMonthIndex = 0,
                dismissListener = {
                    viewModel.stateShowDialogChangeEndDate.value = false
                },
                applyListener = { year, monthIndex ->
                    viewModel.stateShowDialogChangeEndDate.value = false
                }
            )
        }
    }

    @Composable
    fun ChangeDateDialog(initYear: Int, initMonthIndex: Int, dismissListener: () -> Unit, applyListener: (Int, Int) -> Unit) {
        val minYearValue = 2000
        val maxYearValue = 2040
        val months = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )

        val year = remember { mutableStateOf(initYear) }
        val monthIndex = remember { mutableStateOf(initMonthIndex) }

        Dialog (
            onDismissRequest = {
                dismissListener()
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_left),
                            contentDescription = "",
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    if (year.value > minYearValue) {
                                        year.value --
                                    }
                                }
                        )
                        Text(
                            text = year.value.toString(),
                            fontSize = 20.sp
                        )
                        Icon(
                            painterResource(R.drawable.ic_right),
                            contentDescription = "",
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    if (year.value < maxYearValue) {
                                        year.value ++
                                    }
                                }
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .wrapContentHeight()
                    ) {
                        itemsIndexed(months) { i, month ->
                            Text(
                                text = month,
                                fontSize = 18.sp,
                                color = if (i == monthIndex.value) {
                                    Color.White
                                }
                                else {
                                    Color.Black
                                },
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(5.dp, 10.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(if (i == monthIndex.value) Color.Blue else Color.Transparent)
                                    .clickable {
                                        monthIndex.value = i
                                    }
                            )
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
                                applyListener(year.value, monthIndex.value)
                            },
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }

    private fun startTermsActivity() {
        startActivity(Intent(this, TermsActivity::class.java))
    }
}
