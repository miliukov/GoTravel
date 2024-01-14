package com.example.gotravel.ui

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gotravel.R
import com.example.gotravel.data.Airport
import com.example.gotravel.data.IATAToName
import com.example.gotravel.data.PopularDestination
import com.example.gotravel.data.findCity
import com.example.gotravel.data.findPopularDestinations
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRoute(context: Context) {
    var currentIndex by remember { mutableStateOf(0) }
    var destination by remember { mutableStateOf("") }
    var dateLabel by remember { mutableStateOf("") }
    var isDatePicker1Visible by remember { mutableStateOf(false) }
    var isDatePicker2Visible by remember { mutableStateOf(false) }
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
                    .background(LightBlue, shape = RoundedCornerShape(10.dp)),

                ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    showOrigin(context)
                    Canvas(modifier = Modifier.fillMaxWidth()) {
                        drawLine(
                            color = Color.White,
                            start = Offset(60f, 0f),
                            end = Offset(size.width - 150f, 0f),
                            strokeWidth = 1f
                        )
                    }
                    OutlinedTextField( // куда
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 1.dp,
                                start = 10.dp,
                                end = 10.dp
                            ),
                        value = destination,
                        onValueChange = {
                            destination = it

                                        },
                        textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = Color.Blue,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                        )
                    )
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
                        containerColor = LightLightBlue,
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
                            tint = LightBlue,
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
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightLightBlue,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = context.getString(R.string.num_people))
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
            showPopularDestinations("MOW", context)
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
}

@Composable
fun showPopularDestinations(origin: String, context: Context) {
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
                    .width(150.dp).height(150.dp)
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

@Composable
fun showOrigin(context: Context) {
    var origin by remember { mutableStateOf("") }
    val list = remember {
        mutableStateOf<List<Airport>>(emptyList())
    }
    OutlinedTextField( // откуда
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 10.dp,
                bottom = 1.dp,
                end = 10.dp
            ),
        value = origin,
        onValueChange = {
            origin = it
            findCity(origin, context)
        },
        textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Blue,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
        )
    )
}

fun convertMillisToDate(selectedDateMillis: Long): String {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.timeInMillis = selectedDateMillis
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day.$month.$year"
}