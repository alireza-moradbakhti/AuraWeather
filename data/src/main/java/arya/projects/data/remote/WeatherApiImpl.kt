package arya.projects.data.remote

import arya.projects.core.util.AppConstants
import arya.projects.data.dto.WeatherDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class WeatherApiImpl(
    private val client: HttpClient,
    private val apiKey: String
) : WeatherApi {

    override suspend fun getWeather(
        lat: Double,
        lon: Double
    ): WeatherDto {
        return client.get(AppConstants.BASE_URL) {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("appid", apiKey)
            parameter("units", AppConstants.UNIT_METRIC)
        }.body()
    }

}