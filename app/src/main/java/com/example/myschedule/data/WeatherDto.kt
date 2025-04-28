package com.example.myschedule.data

data class WeatherDto(
    val date: String,
    val time: String,
    val rainProbability: String,
    val rainCode: Int,
    val rainAmount: String,
    val snowAmount: String,
    val skyCode: Int,
    val temperature: String
)
