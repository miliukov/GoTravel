package com.example.gotravel.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gotravel.data.Airport
import com.example.gotravel.data.Flight
import com.example.gotravel.data.airlineLogo
import com.example.gotravel.data.findCars
import com.example.gotravel.data.findFlights
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

val flights = mutableStateOf(emptyList<Flight>())
var logo = mutableStateOf<ImageBitmap?>(null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRoute(context: Context, navHostController: NavHostController) {
    // navHostController.navigate("choose")
    if (!isNetworkAvailable(context)) {
        Dialog(onDismissRequest = {  }) {
            Text(text = context.getString(R.string.error_net))
        }
    }
    var isDatePicker1Visible by remember { mutableStateOf(false) }
    var isDatePicker2Visible by remember { mutableStateOf(false) }
    var isPeopleCountVisible by remember { mutableStateOf(false) }
    var isWhereFromVisible by remember { mutableStateOf(false) }
    var isWhereToVisible by remember { mutableStateOf(false) }
    var isNextVisible by remember { mutableStateOf(false) }
    var selectedPeople by remember { mutableStateOf(1) }
    var selectedClass by remember { mutableStateOf(context.getString(R.string.economy)) }
    var selected1Date by remember { mutableStateOf(0L) }
    var selected2Date by remember { mutableStateOf(0L) }
    var date = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    var whereFrom by remember { mutableStateOf(context.getString(R.string.where_from)) }
    var whereTo by remember { mutableStateOf(context.getString(R.string.where_to)) }
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
                            onClick = { isWhereFromVisible = true },
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
                                    text = if (whereFrom == "") context.getString(R.string.where_from) else whereFrom,
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
                            onClick = { isWhereToVisible = true },
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
                                    text = if (whereTo == "") context.getString(R.string.where_to) else whereTo,
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.iter))
                                )
                            }
                        }
                    }
                    Button(
                        onClick = {
                            if (whereFrom != context.getString(R.string.where_from) &&
                                whereTo == context.getString(R.string.where_to)) {
                                whereTo = whereFrom
                                whereFrom = context.getString(R.string.where_from)
                            }
                            else if (whereFrom == context.getString(R.string.where_from) &&
                                whereTo != context.getString(R.string.where_to)) {
                                whereFrom = whereTo
                                whereTo = context.getString(R.string.where_to)
                            }
                            else if (whereFrom != context.getString(R.string.where_from) &&
                                whereTo != context.getString(R.string.where_to)) {
                                whereTo = whereFrom.also { whereFrom = whereTo }
                            }
                                  },
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
                if (isNextVisible) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 20.dp),
                        onClick = { navHostController.navigate("choose") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = goTravel_theme_light_primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Далее")
                    }
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
            Text(
                text = "Выгодные предложения",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 5.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.iter)),
                fontWeight = FontWeight.Bold
            )
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = goTravel_theme_light_primaryContainer
            ) {
                Column {
                    Text(
                        text = "Количество человек:",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                            .padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.iter)),
                        color = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer
                    )
                    Text(
                        text = peopleCount.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 34.sp,
                        fontFamily = FontFamily(Font(R.font.iter)),
                        color = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = { peopleCount++ },
                            modifier = Modifier.padding(start = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = goTravel_theme_light_primary,
                                contentColor = goTravel_theme_light_onPrimary_onSecondary_onTertiary_onError
                            )
                        ) {
                            Text("+")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            onClick = { if (peopleCount > 1 ) peopleCount-- },
                            modifier = Modifier.padding(end = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = goTravel_theme_light_primary,
                                contentColor = goTravel_theme_light_onPrimary_onSecondary_onTertiary_onError
                            )
                        ) {
                            Text("-")
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { isPeopleCountVisible = !isPeopleCountVisible },) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = {
                                isPeopleCountVisible = !isPeopleCountVisible
                                selectedPeople = peopleCount },) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
    if (isWhereFromVisible) {
        var text by remember { if (whereFrom != context.getString(R.string.where_from)) mutableStateOf(whereFrom) else mutableStateOf("") }
        Dialog(
            onDismissRequest = { isWhereFromVisible = false },
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(13.dp),
                color = goTravel_theme_light_primaryContainer
            ) {
                Column {
                    Text("Откуда?",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 34.sp,
                        fontFamily = FontFamily(Font(R.font.iter)),
                        color = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer,
                        fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = Color.Black,
                            focusedBorderColor = goTravel_theme_light_primary,
                            unfocusedBorderColor = goTravel_theme_light_primary)
                    )
                    Button(onClick = {
                        isWhereFromVisible = false
                        whereFrom = text
                        isNextVisible = whereTo != context.getString(R.string.where_to) &&
                                whereFrom != context.getString(R.string.where_from)
                    }) {
                        Text("Далее")
                    }
                }

            }
        }
    }
    if (isWhereToVisible) {
        var text by remember { if (whereTo != context.getString(R.string.where_to)) mutableStateOf(whereTo) else mutableStateOf("") }
        Dialog(
            onDismissRequest = { isWhereToVisible = false },
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(13.dp),
                color = goTravel_theme_light_primaryContainer
            ) {
                Column {
                    Text("Куда?",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 34.sp,
                        fontFamily = FontFamily(Font(R.font.iter)),
                        color = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer,
                        fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = Color.Black,
                            focusedBorderColor = goTravel_theme_light_primary,
                            unfocusedBorderColor = goTravel_theme_light_primary)
                    )
                    Button(onClick = {
                        isWhereToVisible = false
                        whereTo = text
                        isNextVisible = whereTo != context.getString(R.string.where_to) &&
                                whereFrom != context.getString(R.string.where_from)
                    }) {
                        Text("Далее")
                    }
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

@Composable
fun ChooseTrans(context: Context, navHostController: NavHostController) {
    findFlights("SVO", "LHR", "2024-04", context, flights)
    findCars("Москва", "Саратов", context)
    // TODO: loading screen
    Text(
        text = context.getString(R.string.transfer_option),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        textAlign = TextAlign.Center,
        fontSize = 34.sp,
        fontFamily = FontFamily(Font(R.font.iter))
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
            onClick = { navHostController.navigate("by_plane") },
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.airplane),
                        contentDescription = "AirplaneIcon"
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = context.getString(R.string.by_plane),
                        fontWeight = FontWeight.Bold
                        )
                }
                if (flights.value.isNotEmpty()) {
                    Text(
                        text = "В среднем это займет ${flights.value[0].duration / 60} часов, " +
                                "из них ${flights.value[0].duration_to / 60} в полете (${(flights.value[0].duration - flights.value[0].duration_to) / 60} на пересадки), " +
                                "примерное количество пересадок ${flights.value[0].transfers}, " +
                                "авиакомпания ${flights.value[0].airline}",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
            onClick = { navHostController.navigate("by_car") },
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.car),
                        contentDescription = "CarIcon"
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = context.getString(R.string.by_car),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text("это займет столько-то, столько-то, маршруты и др и тд")
            }
        }
    }
}

@Composable
fun ShowRouteFlights(context: Context, navHostController: NavHostController) {
    var selectedFlight = remember { mutableStateOf(Flight("", "", "", 0, "", 0, 0, 0, ""))}
    var isSelectedFlight = remember { mutableStateOf(false)}
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = true
    ) {
        items(flights.value) { ticket ->
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                onClick = {
                    isSelectedFlight.value = true
                    selectedFlight.value = ticket
                          },
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(text = ticket.price.toString())
                        Spacer(Modifier.size(50.dp))
                        Text(text = ticket.date)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = ticket.destination)
                        Spacer(Modifier.size(50.dp))
                        Text(text = ticket.airline)
                    }
                }
            }
        }
    }
    if (isSelectedFlight.value) {
        airlineLogo(selectedFlight.value.airline, context, logo) // TODO: мерцание и постоянно API
        Dialog(
            onDismissRequest = { isSelectedFlight.value = false },
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(13.dp),
                color = goTravel_theme_light_primaryContainer
            ) {
                Column() {
                    Row() {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            fontFamily = FontFamily(Font(R.font.iter)),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            text = "${selectedFlight.value.price}руб."
                        )
                        Spacer(modifier = Modifier.size(50.dp, 1.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            fontFamily = FontFamily(Font(R.font.iter)),
                            fontSize = 20.sp,
                            text = "${selectedFlight.value.duration / 60}ч. ${selectedFlight.value.duration % 60}м."
                        )
                    }
                    Row() {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            fontFamily = FontFamily(Font(R.font.iter)),
                            fontSize = 20.sp,
                            text = "Количество пересадок: ${selectedFlight.value.transfers}"
                        )
                        Image(
                            painter = rememberAsyncImagePainter(logo.value?.asAndroidBitmap()),
                            contentDescription = "Image from server"
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 20.dp),
                        onClick = { navHostController.navigate("choose") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = goTravel_theme_light_primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Далее")
                    }
                }
            }
        }
    }
}

@OptIn(MapboxExperimental::class)
@Composable
fun ShowRoutesCar(context: Context, navHostController: NavHostController) {
//    MapboxMap(
//        Modifier.fillMaxSize(),
//        mapViewportState = MapViewportState().apply {
//            setCameraOptions {
//                zoom(2.0)
//                center(Point.fromLngLat(-98.0, 39.5))
//                pitch(0.0)
//                bearing(0.0)
//            }
//        },
//    )
    Button(onClick = {
        val gmmIntentUri =
            Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(context, mapIntent, null)
    }
    ) {
        Text("BUTTON")
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

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}
