package arya.projects.domain.model

data class WeatherInfo(
    val temperatureCelsius: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val weatherDescription: String,
    val iconUrl: String
)
