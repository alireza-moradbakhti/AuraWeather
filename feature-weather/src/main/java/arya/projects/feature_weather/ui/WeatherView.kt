package arya.projects.feature_weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import arya.projects.core.R
import arya.projects.domain.model.WeatherInfo

@Composable
fun WeatherSuccessView(weatherInfo: WeatherInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.spacing_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.current_weather_title),
            fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        Text(
            text = stringResource(id = R.string.temperature_label, weatherInfo.temperatureCelsius),
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(id = R.string.condition_label, weatherInfo.weatherDescription),
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(id = R.string.humidity_label, weatherInfo.humidity),
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(id = R.string.wind_speed_label, weatherInfo.windSpeed),
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
        )
    }
}