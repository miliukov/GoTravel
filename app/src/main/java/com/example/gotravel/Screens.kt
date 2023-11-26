package com.example.gotravel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, context)
        }
    ) {
        NavGraph(navHostController = navController, context)
    }
}

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
        contentAlignment = Alignment.Center,
    ) {
        //Crossfade(targetState = currentIndex, label = "") { index ->
        //    val imageRes = images[index]
        //    Image(
        //        painter = painterResource(imageRes),
        //        contentDescription = null,
        //        modifier = Modifier.fillMaxSize(),
        //        contentScale = ContentScale.Crop
        //    )
        //}
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(

            ) {
                Column(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(start = 10.dp, end = 5.dp)) {
                    Text(
                        text = context.getString(R.string.where_from),
                        fontSize = 23.sp,
                        fontFamily = FontFamily(Font(R.font.iter))
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(top = 20.dp, bottom = 30.dp),
                        value = origin,
                        onValueChange = { origin = it },
                        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                    )
                    LazyColumn {
                        items(arrayListOf(airports.value)[0]) { airport ->
                            Log.d("MyLog1", airport.toString())
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box {
                                    Text(text = airport.toString())
                                }
                            }
                        }
                    }


                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 10.dp)) {
                    Text(
                        text = context.getString(R.string.where_to),
                        fontSize = 23.sp,
                        fontFamily = FontFamily(Font(R.font.iter))
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(top = 20.dp, bottom = 30.dp),
                        value = destination,
                        onValueChange = { destination = it },
                        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                    )
                }
            }
            DateRangePicker(
                state = date,
                modifier = Modifier.padding(20.dp),
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                elevation = buttonElevation(5.dp),
                onClick = {
                    getRoute(origin, destination, "date", context)
                }
            ) {
                Text(context.getString(R.string.lets_go))
            }
        }
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

fun getRoute(origin: String, destination: String, date: String, context: Context) {
    val flights = findFlights(origin, destination, date, context)
    val trains = findTrains(origin, destination, date, context)
    val cars = findCars(origin, destination, date, context)

}

@Composable
fun AirTickets(context: Context) {
    showFlights(findFlights("GSV", "SVO", "2023-11", context))
}
@Composable
fun Hotels() {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Hotels here",
        textAlign = TextAlign.Center
    )
}
@Composable
fun Profile() {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Profile here",
        textAlign = TextAlign.Center
    )
}

@Composable
fun NavGraph(
    navHostController: NavHostController,
    context: Context
) {
    NavHost(navController = navHostController, startDestination = "screen_1"){
        composable("screen_1"){
            showRoute(context)
        }
        composable("screen_2"){
            AirTickets(context)
        }
        composable("screen_3"){
            Hotels()
        }
        composable("screen_4"){
            Profile()
        }
    }
}

