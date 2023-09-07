package de.mm20.launcher2.weather.pirateweather

import android.content.Context
import android.content.SharedPreferences
import de.mm20.launcher2.weather.Forecast
import de.mm20.launcher2.weather.LatLonWeatherLocation
import de.mm20.launcher2.weather.LatLonWeatherProvider
import de.mm20.launcher2.weather.R
import de.mm20.launcher2.weather.WeatherUpdateResult
import de.mm20.launcher2.weather.brightsky.BrightskyProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.math.roundToInt

class PirateWeatherProvider(
    override val context: Context
) : LatLonWeatherProvider() {

    private val apiClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pirateweather.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create<PirateWeatherApi>()
    }

    override val preferences: SharedPreferences
        get() = context.getSharedPreferences(BrightskyProvider.PREFS, Context.MODE_PRIVATE)

    override val usesApiKey: Boolean = true

    override suspend fun getApiKey(): String? {
        return super.getApiKey() ?: ""
    }

    override fun isUpdateRequired(): Boolean {
        return getLastUpdate() + 3600000 < System.currentTimeMillis()
    }

    override fun isAvailable(): Boolean {
        return true
    }

    override val name: String
        get() = context.getString(R.string.provider_pirateweather)

    override suspend fun loadWeatherData(location: LatLonWeatherLocation): WeatherUpdateResult<LatLonWeatherLocation>? {
        val apiKey = getApiKey() ?: return null
        val latLng = "${location.lat},${location.lon}"
        val forecastData = apiClient.getForecast(apiKey, latLng, "us", extend = true)

        val updateTime = System.currentTimeMillis()
        val forecasts = forecastData.hourly.data.map {
            Forecast(
                timestamp = it.time,
                temperature = it.temperature,
                minTemp = it.temperature,
                maxTemp = it.temperature,
                pressure = it.pressure,
                humidity = it.humidity,
                icon = getIcon(it.icon) ?: Forecast.CLEAR,
                condition = getCondition(it.icon),
                clouds = it.cloudCover.roundToInt(),
                windSpeed = it.windSpeed,
                windDirection = it.windBearing.toDouble(),
                precipitation = it.precipIntensity,
                night = (it.uvIndex ?: 0.0).roundToInt() == 0,
                location = location.name,
                provider = "NOAA",
                providerUrl = "https://www.weather.gov",
                precipProbability = it.precipProbability.toInt(),
                updateTime = updateTime
            )
        }

        return WeatherUpdateResult(forecasts, location)
    }

    private fun getIcon(icon: String): Int {
        return when (icon) {
            "clear-day", "clear-night" -> Forecast.CLEAR
            "partly-cloudy-day", "partly-cloudy-night" -> Forecast.PARTLY_CLOUDY
            "cloudy" -> Forecast.CLOUDY
            "fog" -> Forecast.FOG
            "wind" -> Forecast.WIND
            "rain" -> Forecast.SHOWERS
            "sleet" -> Forecast.SLEET
            "snow" -> Forecast.SNOW
            "hail" -> Forecast.HAIL
            "thunderstorm" -> Forecast.THUNDERSTORM
            else -> Forecast.CLEAR
        }
    }

    private fun getCondition(icon: String): String {
        return context.getString(
            when (icon) {
                "clear-day", "clear-night" -> R.string.weather_condition_clearsky
                "partly-cloudy-day", "partly-cloudy-night" -> R.string.weather_condition_partlycloudy
                "cloudy" -> R.string.weather_condition_cloudy
                "fog" -> R.string.weather_condition_fog
                "wind" -> R.string.weather_condition_wind
                "rain" -> R.string.weather_condition_rain
                "sleet" -> R.string.weather_condition_sleet
                "snow" -> R.string.weather_condition_snow
                "hail" -> R.string.weather_condition_hail
                "thunderstorm" -> R.string.weather_condition_thunder
                else -> R.string.weather_condition_clearsky
            }
        )
    }

}