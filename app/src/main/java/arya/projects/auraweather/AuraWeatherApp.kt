package arya.projects.auraweather

import android.app.Application
import arya.projects.auraweather.di.appModule
import arya.projects.data.di.dataModule
import arya.projects.feature_weather.di.featureWeatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class AuraWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin activity to Logcat
            androidLogger()
            // Declare Android context for Koin
            androidContext(this@AuraWeatherApp)
            // Declare modules to load. We will populate these files later.
            modules(
                appModule,
                dataModule,
                featureWeatherModule
            )
        }
    }
}