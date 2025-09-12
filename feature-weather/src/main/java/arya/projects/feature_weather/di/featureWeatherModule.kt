package arya.projects.feature_weather.di

import arya.projects.domain.usecase.GetCurrentWeatherUseCase
import arya.projects.feature_weather.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureWeatherModule = module {
    // Provides an instance of our UseCase using the modern factoryOf DSL
    factoryOf(::GetCurrentWeatherUseCase)

    // Provides an instance of our ViewModel using the modern viewModelOf DSL for constructor injection
    viewModelOf(::WeatherViewModel)
}