package arya.projects.data.repository

import arya.projects.core.util.AppConstants
import arya.projects.data.mapper.toWeatherInfo
import arya.projects.data.remote.WeatherApi
import arya.projects.domain.model.WeatherInfo
import arya.projects.domain.repository.WeatherRepository
import arya.projects.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override fun getCurrentWeather(
        lat: Double,
        long: Double
    ): Flow<Resource<WeatherInfo>> = flow {
        emit(Resource.Loading())

        try {
            // Fetch data from the API
            val remoteWeather = api.getWeather(lat, long)
            // Map the DTO to our domain model
            val weatherInfo = remoteWeather.toWeatherInfo()
            // Emit success with the data
            emit(Resource.Success(weatherInfo))
        } catch (e: IOException) {
            // Handle network errors (e.g., no internet)
            emit(Resource.Error(AppConstants.ERROR_NETWORK))
        } catch (e: Exception) {
            // Handle other errors (e.g., malformed JSON)
            emit(Resource.Error("${AppConstants.ERROR_GENERIC} : ${e.message}"))
        }
    }

}