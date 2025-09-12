package arya.projects.data.mapper

import arya.projects.core.util.AppConstants
import arya.projects.data.dto.WeatherDto
import arya.projects.domain.model.WeatherInfo

/**
 * Extension function to map WeatherDto to WeatherInfo
 * @param WeatherDto The data transfer object containing weather data
 * @return WeatherInfo The domain model containing mapped weather information
 */
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDescription = weather.firstOrNull()
    return WeatherInfo(
        temperatureCelsius = main.temp,
        pressure = main.pressure,
        humidity = main.humidity,
        windSpeed = wind.speed,
        weatherDescription = weatherDescription?.description ?: "Unknown",
        iconUrl = "${AppConstants.ICON_URL}/${weatherDescription?.icon}@2x.png"
    )
}
