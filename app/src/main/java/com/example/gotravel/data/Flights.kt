package com.example.gotravel.data

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import coil.compose.rememberAsyncImagePainter
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

fun findFlights(
    origin: String,
    destination: String,
    date: String,
    context: Context,
    tickets: MutableState<List<Flight>>
) {
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
                        ticket.getString("price").toInt(),
                        ticket.getString("transfers").toInt(),
                        ticket.getString("duration_to").toInt(),
                        ticket.getString("link")

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
}

@Composable
fun airlineLogo(code: String, context: Context, logo: MutableState<ImageBitmap?>) {
    val url = "https://pics.avs.io/100/100/$code.png"
    val queue = Volley.newRequestQueue(context)
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val imageRequest = ImageRequest(url,
        { response ->
            imageBitmap = response.asImageBitmap()
            Log.d("MyLog4", "lalala")
        },0,
        0,
        ImageView.ScaleType.CENTER_CROP,
        Bitmap.Config.RGB_565,
        { error: VolleyError? ->
            Log.d("MyLog5", error.toString())
        })
    logo.value = imageBitmap
    queue.add(imageRequest)
}