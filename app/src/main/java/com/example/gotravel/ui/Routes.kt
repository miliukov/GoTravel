package com.example.gotravel.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R
import com.example.gotravel.data.findAirports
import com.example.gotravel.data.getRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showRoute(context: Context) {
    //val images = listOf(

    //)
    var currentIndex by remember { mutableStateOf(0) }
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    val airports = findAirports(origin, context)
    var date = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Input)
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Text(text = "Куда отправимся?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center,
                fontSize = 34.sp,
                fontFamily = FontFamily(Font(R.font.iter)))
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .background(LightBlue, shape = RoundedCornerShape(10.dp)),

                ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                        onValueChange = { origin = it },
                        textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = Color.Blue,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                        )
                    )
                    //LazyColumn { // T9
                    //    items(arrayListOf(airports.value)[0]) { airport ->
                    //        Log.d("MyLog1", airport.toString())
                    //        Card(
                    //            modifier = Modifier
                    //                .fillMaxWidth()
                    //        ) {
                    //            Box {
                    //                Text(text = airport.toString())
                    //            }
                    //        }
                    //    }
                    //}
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
                        onValueChange = { destination = it },
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
            Row() {
                Button(onClick = {  },
                    modifier = Modifier.padding(end = 5.dp, start = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightLightBlue,
                        contentColor = Color.Black),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Даты")
                }
                Button(onClick = {  },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightLightBlue,
                        contentColor = Color.Black),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Кол-во человек")
                }
            }
        }

            //DateRangePicker(
        //    state = date,
        //    modifier = Modifier.padding(20.dp),
        //)
        //Button(
        //    modifier = Modifier
        //        .fillMaxWidth()
        //        .padding(10.dp),
        //    elevation = ButtonDefaults.buttonElevation(5.dp),
        //    onClick = {
        //        getRoute(origin, destination, "date", context)
        //    }
        //) {
        //    Text(context.getString(R.string.lets_go))
        //}
    }
    //LaunchedEffect(Unit) {
    //    while (true) {
    //        delay(10000)
    //        withContext(Dispatchers.Main) {
    //            currentIndex = (currentIndex + 1) % images.size
    //        }
    //    }
    //}
}