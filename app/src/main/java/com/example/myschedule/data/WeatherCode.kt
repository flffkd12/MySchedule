package com.example.myschedule.data

enum class WeatherCode(val dangerLevel: Int) {
    ERROR(0),
    SUNNY(1),
    SUNNY_WITH_CLOUD(2),
    CLOUDY(3),
    RAINY(4),
    SNOWY(5)
}
