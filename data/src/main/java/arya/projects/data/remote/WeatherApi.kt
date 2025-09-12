package arya.projects.data.remote

import arya.projects.data.dto.WeatherDto

interface WeatherApi {

    suspend fun getWeather(lat: Double, lon: Double): WeatherDto

}