package com.example.gotravel.data

data class Flight(
    val origin: String,
    val destination: String,
    val date: String,
    val duration: Int,
    val airline: String,
    val price: Int,
    val transfers: Int,
    var duration_to: Int,
    val url: String = ""
)
