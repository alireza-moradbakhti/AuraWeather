package arya.projects.data.di

import arya.projects.data.remote.WeatherApi
import arya.projects.data.remote.WeatherApiImpl
import arya.projects.data.repository.WeatherRepositoryImpl
import arya.projects.domain.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    // Provides a singleton instance of the Ktor HttpClient
    single {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    // Provides an instance of our WeatherApi, injecting the HttpClient and the API key
    single<WeatherApi> {
        WeatherApiImpl(
            client = get(), // Koin will find the HttpClient we defined above
            // We now ask Koin to provide a String with the specific name "weather_api_key"
            apiKey = get(named("weather_api_key"))
        )
    }

    // Provides an instance of our WeatherRepository, injecting the WeatherApi
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            api = get() // Koin will find the WeatherApi we defined above
        )
    }

}