package com.example.gotravel.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gotravel.data.Flight

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