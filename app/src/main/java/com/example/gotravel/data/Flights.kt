package com.example.gotravel.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

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