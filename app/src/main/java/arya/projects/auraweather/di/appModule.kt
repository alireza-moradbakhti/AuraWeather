package arya.projects.auraweather.di

import arya.projects.data.di.dataModule
import arya.projects.feature_weather.di.featureWeatherModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        featureWeatherModule
    )

}