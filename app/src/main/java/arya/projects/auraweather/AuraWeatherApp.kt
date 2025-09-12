package arya.projects.auraweather

import android.app.Application
import arya.projects.auraweather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


class AuraWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // A new Koin module specifically to provide the API key
        val apiKeyModule = module {
            // This provides the actual API key string from BuildConfig
            // and gives it the name "weather_api_key"
            single(named("weather_api_key")) {
                BuildConfig.WEATHER_API_KEY
            }
        }

        startKoin {
            // Log Koin activity to Logcat
            androidLogger()
            // Declare Android context for Koin
            androidContext(this@AuraWeatherApp)
            // Declare modules to load. We will populate these files later.
            modules(
                appModule,
                apiKeyModule
            )
        }
    }
}