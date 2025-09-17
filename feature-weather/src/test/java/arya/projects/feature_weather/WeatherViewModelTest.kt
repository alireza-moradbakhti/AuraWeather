package arya.projects.feature_weather

import app.cash.turbine.test
import arya.projects.domain.model.WeatherInfo
import arya.projects.domain.repository.WeatherRepository
import arya.projects.domain.usecase.GetCurrentWeatherUseCase
import arya.projects.domain.util.Resource
import arya.projects.feature_weather.ui.WeatherState
import arya.projects.feature_weather.viewmodel.WeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineExtension::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var useCase: GetCurrentWeatherUseCase
    private val repository: WeatherRepository = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        useCase = GetCurrentWeatherUseCase(repository)
        viewModel = WeatherViewModel(useCase)
    }

    @Test
    fun `loadWeatherInfo - on success - state is updated with weather data`() = runTest {
        // Given: The repository will return Loading then Success
        val mockWeatherInfo = WeatherInfo(
            temperatureCelsius = 25.0,
            pressure = 1012,
            humidity = 60,
            windSpeed = 15.0,
            weatherDescription = "Sunny",
            iconUrl = "http://example.com/icon.png"
        )
        coEvery { repository.getCurrentWeather(any(), any()) } returns flow {
            emit(Resource.Loading())
            yield() // <-- The fix: allows the collector to process the loading state
            emit(Resource.Success(mockWeatherInfo))
        }

        // When & Then: The state flow is tested
        viewModel.state.test {
            // First, await the initial state before doing anything
            assertEquals(WeatherState(), awaitItem())

            // Now, trigger the action
            viewModel.loadWeatherInfo(25.0, 75.0)

            // Await the loading state
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            // Await the success state
            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(mockWeatherInfo, successState.weatherInfo)
            assertEquals(null, successState.error)

            // Ensure no more state changes happen
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadWeatherInfo - on error - state is updated with error message`() = runTest {
        // Given: The repository will return Loading then Error
        val errorMessage = "Could not fetch weather"
        coEvery { repository.getCurrentWeather(any(), any()) } returns flow {
            emit(Resource.Loading())
            yield() // <-- The fix: allows the collector to process the loading state
            emit(Resource.Error(errorMessage))
        }

        // When & Then: The state flow is tested
        viewModel.state.test {
            // First, await the initial state
            assertEquals(WeatherState(), awaitItem())

            // Now, trigger the action
            viewModel.loadWeatherInfo(25.0, 75.0)

            // Await the loading state
            assertEquals(true, awaitItem().isLoading)

            // Await the error state
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals(null, errorState.weatherInfo)
            assertEquals(errorMessage, errorState.error)

            // Ensure no more state changes happen
            cancelAndIgnoreRemainingEvents()
        }
    }
}