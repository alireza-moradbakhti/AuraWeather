package arya.projects.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * These data classes are designed to perfectly match the JSON structure
 * of the OpenWeatherMap API response. @SerialName is used when the JSON key
 * doesn't match our desired variable name.
 **/

@Serializable
data class WeatherDto(
    @SerialName("main")
    val main: MainDto,
    @SerialName("wind")
    val wind: WindDto,
    @SerialName("weather")
    val weather: List<WeatherDescriptionDto>
)

@Serializable
data class MainDto(
    @SerialName("temp")
    val temp: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int
)

@Serializable
data class WindDto(
    @SerialName("speed")
    val speed: Double
)

@Serializable
data class WeatherDescriptionDto(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)
