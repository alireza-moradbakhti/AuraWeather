package arya.projects.domain.repository

import arya.projects.domain.model.WeatherInfo
import arya.projects.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>>
}