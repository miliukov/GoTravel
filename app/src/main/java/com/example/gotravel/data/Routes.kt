package com.example.gotravel.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

//fun getRoute(origin: String, destination: String, date: String, context: Context, ) {
//    val tickets: MutableState<List<Flight>> = mutableStateOf(emptyList())
//    val flights = findFlights(origin, destination, date, context, tickets)
//    val cars = findCars(origin, destination, date, context)
//}

fun findPopularDestinations(
    origin: String,
    state: MutableState<List<PopularDestination>>,
    context: Context
) {
    val url = "https://api.travelpayouts.com/v1/city-directions" +
            "?origin=$origin&" +
            "currency=rub&" +
            "token=9941a14a305a272151185f303fe70807"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val data = JSONObject(response).getJSONObject("data")
            val airportCodes = data.keys()
            val currentList = state.value.toMutableList()
            val destinationName = mutableStateOf("")
            for (key in airportCodes) {
                IATAToName(key, context, destinationName)
                val ticket = data.getJSONObject(key)
                currentList.add(
                    PopularDestination(
                        ticket.getString("destination"),
                        destinationName.value,
                        ticket.getString("departure_at"),
                        ticket.getString("return_at"),
                        ticket.getString("airline"),
                        ticket.getString("price").toInt(),
                    )
                )
            }
            state.value = currentList
        },
        { error ->
            Log.e("MyLog3", error.toString())
            state.value = emptyList()
        }
    )
    queue.add(stringRequest)
}

fun IATAToName(IATA: String, context: Context, name: MutableState<String>) {
    val url = "https://autocomplete.travelpayouts.com/places2?locale=ru" +
            "&types[]=airport&types[]=city" +
            "&term=$IATA"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val data = JSONArray(response).getJSONObject(0).getString("name")
            name.value = data
        },
        { error ->
            Log.e("MyLog3", error.toString())
        }
    )
    queue.add(stringRequest)
}

fun findCity(printed: String, context: Context) {
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
}