package arya.projects.feature_weather

import app.cash.turbine.test
import arya.projects.domain.model.WeatherInfo
import arya.projects.domain.repository.WeatherRepository
import arya.projects.domain.usecase.GetCurrentWeatherUseCase
import arya.projects.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCurrentWeatherUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @BeforeEach
    fun setUp() {
        weatherRepository = mockk()
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)
    }

    @Test
    fun `invoke should emit success resource when repository is successful`() = runTest {
        // Given
        val mockWeatherInfo = WeatherInfo(
            temperatureCelsius = 25.0,
            pressure = 10,
            iconUrl = "http://example.com/icon.png",
            weatherDescription = "Sunny",
            humidity = 50,
            windSpeed = 10.0
        )
        val lat = 51.5
        val long = -0.1
        coEvery { weatherRepository.getCurrentWeather(lat, long) } returns flowOf(Resource.Success(mockWeatherInfo))

        // When
        val resultFlow = getCurrentWeatherUseCase(lat, long)

        // Then
        resultFlow.test {
            val successItem = awaitItem()
            assertEquals(mockWeatherInfo, (successItem as Resource.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should emit error resource when repository fails`() = runTest {
        // Given
        val errorMessage = "Network Error"
        val lat = 51.5
        val long = -0.1
        coEvery { weatherRepository.getCurrentWeather(lat, long) } returns flowOf(Resource.Error(errorMessage))

        // When
        val resultFlow = getCurrentWeatherUseCase(lat, long)

        // Then
        resultFlow.test {
            val errorItem = awaitItem()
            assertEquals(errorMessage, (errorItem as Resource.Error).message)
            awaitComplete()
        }
    }
}