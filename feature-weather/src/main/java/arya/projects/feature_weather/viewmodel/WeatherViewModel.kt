package arya.projects.feature_weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arya.projects.domain.usecase.GetCurrentWeatherUseCase
import arya.projects.domain.util.Resource
import arya.projects.feature_weather.ui.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class WeatherViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun loadWeatherInfo(lat: Double, long: Double) {
        getCurrentWeatherUseCase(lat, long)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

}