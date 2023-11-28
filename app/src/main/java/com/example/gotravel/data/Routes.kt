package com.example.gotravel.data

import android.content.Context

fun getRoute(origin: String, destination: String, date: String, context: Context) {
    val flights = findFlights(origin, destination, date, context)
    val trains = findTrains(origin, destination, date, context)
    val cars = findCars(origin, destination, date, context)
}