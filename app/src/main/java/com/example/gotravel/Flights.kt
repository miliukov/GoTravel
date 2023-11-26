package com.example.gotravel

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

data class Flight(
    val origin: String,
    val destination: String,
    val date: String,
    val duration: Int,
    val airline: String,
    val price: Int
)

data class Airport(
    val name: String,
    val code: String,
    val countryName: String,
    val cityName: String,
    val coordinates: String
)

fun findAirports(printed: String, context: Context): MutableState<List<Airport>> {
    val airports: MutableState<List<Airport>> = mutableStateOf(emptyList())
    val locale = context.resources.configuration.locales[0].language
    val url = "https://autocomplete.travelpayouts.com/places2?locale=$locale&" +
            "types[]=airport&types[]=city&term=$printed"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val obj = JSONArray(response)
            val airportList = mutableListOf<Airport>()
            for (i in 0 until obj.length()) {
                val jsonObject = obj.getJSONObject(i)
                if (jsonObject.getString("type") == "airport") {
                    airportList.add(
                        Airport(
                            jsonObject.getString("name"),
                            jsonObject.getString("code"),
                            jsonObject.getString("country_name"),
                            jsonObject.getString("city_name"),
                            jsonObject.getString("coordinates")))
                }
            }
            airports.value = airportList
        },
        { error ->
            airports.value = emptyList()
        }
    )
    queue.add(stringRequest)
    Log.d("MyLog", airports.value.toString())
    return airports
}

fun findFlights(
    origin: String,
    destination: String,
    date: String,
    context: Context
): MutableState<List<Flight>> {
    val tickets: MutableState<List<Flight>> = mutableStateOf(emptyList())
    val url = "https://api.travelpayouts.com/aviasales/v3/prices_for_dates?" +
            "origin=$origin&" +
            "destination=$destination&" +
            "limit=31&" +
            "departure_at=$date&" +
            "token=9941a14a305a272151185f303fe70807"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val data = JSONObject(response).getJSONArray("data")
            val ticketList = mutableListOf<Flight>()
            for (i in 0 until data.length()) {
                val ticket = data.getJSONObject(i)
                ticketList.add(
                    Flight(
                        ticket.getString("origin_airport"),
                        ticket.getString("destination_airport"),
                        ticket.getString("departure_at"),
                        ticket.getString("duration").toInt(),
                        ticket.getString("airline"),
                        ticket.getString("price").toInt()
                    )
                )
            }
            tickets.value = ticketList
        },
        { error ->
            tickets.value = emptyList()
        }
    )
    queue.add(stringRequest)
    return tickets
}

@Composable
fun showFlights(tickets_m: MutableState<List<Flight>>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = true
    ) {
        items(arrayListOf(tickets_m.value)[0]) { ticket ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(start = 10.dp)
                        ) {
                            Row(
                                
                            ) {
                                Text(text = ticket.airline)
                                AsyncImage(
                                    model = "http://pics.avs.io/200/200/${ticket.airline}.png",
                                    contentDescription = null,
                                )
                            }
                            Text(text = "buy")
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = ticket.duration.toString())
                            Text(text = ticket.price.toString())
                        }
                    }
                }
            }
        }
    }
}