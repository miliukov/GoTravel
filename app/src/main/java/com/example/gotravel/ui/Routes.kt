package com.example.gotravel.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gotravel.R
import com.example.gotravel.data.PopularDestination
import com.example.gotravel.data.findPopularDestinations
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRoute(context: Context) {
    var isDatePicker1Visible by remember { mutableStateOf(false) }
    var isDatePicker2Visible by remember { mutableStateOf(false) }
    var isPeopleCountVisible by remember { mutableStateOf(false) }
    var selectedPeople by remember { mutableStateOf(1) }
    var selectedClass by remember { mutableStateOf(context.getString(R.string.economy)) }
    var selected1Date by remember { mutableStateOf(0L) }
    var selected2Date by remember { mutableStateOf(0L) }
    var date = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Text(
                text = context.getString(R.string.where_are_we_going),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center,
                fontSize = 34.sp,
                fontFamily = FontFamily(Font(R.font.iter))
            )
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .background(goTravel_theme_light_primary, shape = RoundedCornerShape(10.dp)),

                ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.90f)
                    ) {
                        Button(
                            onClick = { /*что-то*/ },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 1.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = goTravel_theme_light_primary,
                                contentColor = Color.White,
                            ),
                            contentPadding = PaddingValues(start = 5.dp, top = 12.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = context.getString(R.string.moscow),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.iter))
                                )
                            }
                        }
                        Canvas(
                            modifier = Modifier
                                .size(320.dp, 1.dp)
                                .padding(start = 15.dp),) {
                            drawLine(
                                color = Color.White,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 1f
                            )
                        }
                        Button(
                            onClick = { /*что-то*/ },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 1.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = goTravel_theme_light_primary,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(start = 5.dp, bottom = 12.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = context.getString(R.string.where_to),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.iter))
                                )
                            }
                        }
                    }
                    Button(
                        onClick = { /*что-то*/ },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = goTravel_theme_light_primary,
                        ),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrows),
                            contentDescription = "Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Button(
                    onClick = {
                        isDatePicker1Visible = !isDatePicker1Visible
                    },
                    modifier = Modifier.padding(end = 5.dp, start = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = goTravel_theme_light_primaryContainer,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "Icon",
                            tint = goTravel_theme_light_primary,
                            modifier = Modifier
                                .size(26.dp)
                                .padding(end = 2.dp)
                        )
                        Text(
                            text = if (selected1Date == 0L && selected2Date == 0L)
                                context.getString(R.string.dates)
                            else if (selected1Date != 0L && selected2Date == 0L)
                                convertMillisToDate(selected1Date)
                            else
                                "${convertMillisToDate(selected1Date)} - ${
                                    convertMillisToDate(
                                        selected2Date
                                    )
                                }"
                        )
                    }

                }
                Button(
                    onClick = { isPeopleCountVisible = !isPeopleCountVisible },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = goTravel_theme_light_primaryContainer,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "$selectedPeople, $selectedClass")
                }
            }
            Text(
                text = context.getString(R.string.popular_destinations),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 5.dp),
                //textAlign = TextAlign,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.iter)),
                fontWeight = FontWeight.Bold
            )
            ShowPopularDestinations("MOW", context)
        }
    }

    if (isDatePicker1Visible) {
        DatePickerDialog(onDismissRequest = { isDatePicker1Visible = false },
            confirmButton = {
                TextButton(onClick = {
                    if (date.selectedDateMillis != null) {
                        isDatePicker1Visible = false
                        isDatePicker2Visible = true
                        selected1Date = date.selectedDateMillis!!
                    }
                }) {
                    Text(text = context.getString(R.string.confirm))
                } },
            dismissButton = {
                TextButton(onClick = {
                    isDatePicker1Visible = false
                }) {
                    Text(text = context.getString(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = date
            )
        }
    }
    if (isDatePicker2Visible) {
        DatePickerDialog(onDismissRequest = { isDatePicker1Visible = false },
            confirmButton = {
                TextButton(onClick = {
                    if (date.selectedDateMillis != null) {
                        isDatePicker2Visible = false
                        selected2Date = date.selectedDateMillis!!
                    }
                }) {
                    Text(text = context.getString(R.string.confirm))
                } },
            dismissButton = {
                TextButton(onClick = {
                    isDatePicker2Visible = false
                }) {
                    Text(text = context.getString(R.string.no_return))
                }
            }
        ) {
            DatePicker(
                state = date
            )
        }
    }
    if (isPeopleCountVisible) {
        var peopleCount by remember { mutableStateOf(selectedPeople) }
        Dialog(
            onDismissRequest = { isPeopleCountVisible = !isPeopleCountVisible },

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = peopleCount.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
                Row() {
                    Button(
                        onClick = { peopleCount++ }
                    ) {
                        Text("+")
                    }
                    Button(
                        onClick = { if (peopleCount > 1 ) peopleCount-- }
                    ) {
                        Text("-")
                    }
                }
                TextButton(
                    onClick = { isPeopleCountVisible = !isPeopleCountVisible },
                ) {
                    Text("Dismiss")
                }
                TextButton(
                    onClick = {
                        isPeopleCountVisible = !isPeopleCountVisible
                        selectedPeople = peopleCount

                    },
                ) {
                    Text("Confirm")
                }
            }
        }

    }
}

@Composable
fun ShowPopularDestinations(origin: String, context: Context) {
    val state = remember {
        mutableStateOf<List<PopularDestination>>(emptyList())
    }
    findPopularDestinations(origin, state, context)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = true
    ) {
        items(state.value) { destination ->
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = destination.destinationIATA,
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.iter)),
                        fontWeight = FontWeight.Bold,
                        color =  Color.White
                        )
                }
            }
        }
    }
}

fun convertMillisToDate(selectedDateMillis: Long): String {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.timeInMillis = selectedDateMillis
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day.$month.$year"
}