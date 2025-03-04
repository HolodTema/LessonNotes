package com.terabyte.lessonnotes.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.ui.theme.LessonNotesTheme
import com.terabyte.lessonnotes.util.DateHelper
import com.terabyte.lessonnotes.viewmodel.CreateTermViewModel

class CreateTermActivity : ComponentActivity() {
    private lateinit var viewModel: CreateTermViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateTermViewModel::class.java]

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
    fun Main(viewModel: CreateTermViewModel, paddingVals: PaddingValues) {
        val regexTermNumber = Regex("[0123456789]*")
        val maxLenNumber = 5

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingVals)
                .verticalScroll(scrollState)
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
                    viewModel.textCreateNewTerm,
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium,
                    color = colorScheme.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            TextField(
                value = viewModel.stateTermNumber.value,
                label = {
                    Text(viewModel.textTermNumber)
                },
                singleLine = true,
                onValueChange = {
                    if (regexTermNumber.matches(it) && it.length <= maxLenNumber) {
                        viewModel.stateTermNumber.value = it
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = colorScheme.secondary,
                    unfocusedLabelColor = colorScheme.secondary,
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    focusedTextColor = colorScheme.secondary,
                    unfocusedTextColor = colorScheme.secondary,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "${viewModel.textStartsAt} ${DateHelper.monthIndexToMonthString(viewModel.stateStartMonthIndex.value)} ${viewModel.stateStartYear.value}",
                    style = typography.bodyMedium,
                    color = colorScheme.secondary,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(end = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.stateShowDialogChangeStartDate.value = true
                    }
                ) {
                    Text(viewModel.textChange)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "${viewModel.textEndsAt} ${DateHelper.monthIndexToMonthString(viewModel.stateEndMonthIndex.value)} ${viewModel.stateEndYear.value}",
                    modifier = Modifier
                        .width(180.dp)
                        .padding(end = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.stateShowDialogChangeEndDate.value = true
                    }
                ) {
                    Text(viewModel.textChange)
                }
            }
            TextField(
                value = viewModel.stateTermDescription.value,
                label = {
                    Text(viewModel.textTermDescription)
                },
                onValueChange = {
                    viewModel.stateTermDescription.value = it
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = colorScheme.secondary,
                    unfocusedLabelColor = colorScheme.secondary,
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    focusedTextColor = colorScheme.secondary,
                    unfocusedTextColor = colorScheme.secondary,
                ),
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    enabled = (viewModel.stateTermNumber.value.isNotEmpty()),
                    onClick = {
                        createTerm()
                    },
                ) {
                    Text(viewModel.textCreate)
                }
            }
        }

        if (viewModel.stateShowDialogChangeStartDate.value) {
            ChangeDateDialog(
                initYear = DateHelper.getCurrentYear(),
                initMonthIndex = DateHelper.getCurrentMonthIndex(),
                dismissListener = {
                    viewModel.stateShowDialogChangeStartDate.value = false
                },
                applyListener = { year, monthIndex ->
                    viewModel.stateStartMonthIndex.value = monthIndex
                    viewModel.stateStartYear.value = year
                    viewModel.stateShowDialogChangeStartDate.value = false
                }
            )
        }

        if (viewModel.stateShowDialogChangeEndDate.value) {
            ChangeDateDialog(
                initYear = DateHelper.getCurrentYear(),
                initMonthIndex = DateHelper.getCurrentMonthIndex(),
                dismissListener = {
                    viewModel.stateShowDialogChangeEndDate.value = false
                },
                applyListener = { year, monthIndex ->
                    viewModel.stateEndMonthIndex.value = monthIndex
                    viewModel.stateEndYear.value = year
                    viewModel.stateShowDialogChangeEndDate.value = false
                }
            )
        }
    }

    @Composable
    fun ChangeDateDialog(initYear: Int, initMonthIndex: Int, dismissListener: () -> Unit, applyListener: (Int, Int) -> Unit) {
        val minYearValue = 2000
        val maxYearValue = 2040

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
                        itemsIndexed(viewModel.months) { i, month ->
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
                            Text(viewModel.textDone)
                        }
                    }
                }
            }
        }
    }

    private fun createTerm() {
        val startMonth = DateHelper.monthIndexToMonthString(viewModel.stateStartMonthIndex.value)
        val endMonth = DateHelper.monthIndexToMonthString(viewModel.stateEndMonthIndex.value)

        val term = Term(
            0,
            viewModel.stateTermNumber.value.toInt(),
            "${startMonth} ${viewModel.stateStartYear.value} - ${endMonth} ${viewModel.stateEndYear.value}",
            viewModel.stateTermDescription.value
        )
        (application as MyApplication).roomManager.insertTerm(term)
        startTermsActivity()
    }

    private fun startTermsActivity() {
        startActivity(Intent(this, TermsActivity::class.java))
    }
}
