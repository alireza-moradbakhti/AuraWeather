package arya.projects.domain.usecase

import arya.projects.domain.repository.WeatherRepository

class GetCurrentWeatherUseCase (
    private val repository: WeatherRepository
){
    operator fun invoke(lat: Double, long: Double) = repository.getCurrentWeather(lat, long)
}