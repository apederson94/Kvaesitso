package de.mm20.launcher2.weather.pirateweather

import android.content.Context
import android.content.SharedPreferences
import de.mm20.launcher2.weather.WeatherLocation
import de.mm20.launcher2.weather.WeatherProvider
import de.mm20.launcher2.weather.WeatherUpdateResult
import de.mm20.launcher2.weather.brightsky.BrightskyProvider
import de.mm20.launcher2.weather.openweathermap.OpenWeatherMapLocation

class PirateWeatherProvider(
    override val context: Context
): WeatherProvider<OpenWeatherMapLocation>() {
    override val preferences: SharedPreferences
        get() = context.getSharedPreferences(BrightskyProvider.PREFS, Context.MODE_PRIVATE)

    override val usesApiKey: Boolean = true

    override suspend fun getApiKey(): String? {
        return super.getApiKey() ?: ""
    }

    override suspend fun loadWeatherData(
        lat: Double,
        lon: Double
    ): WeatherUpdateResult<OpenWeatherMapLocation>? {
        TODO("Not yet implemented")
    }

    override fun isUpdateRequired(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun lookupLocation(query: String): List<OpenWeatherMapLocation> {
        TODO("Not yet implemented")
    }

    override fun setLocation(location: WeatherLocation?) {
        TODO("Not yet implemented")
    }

    override fun getLocation(): OpenWeatherMapLocation? {
        TODO("Not yet implemented")
    }

    override fun isAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override val name: String
        get() = TODO("Not yet implemented")

    override fun getLastLocation(): OpenWeatherMapLocation? {
        TODO("Not yet implemented")
    }

    override fun saveLastLocation(location: OpenWeatherMapLocation) {
        TODO("Not yet implemented")
    }

    override suspend fun loadWeatherData(location: OpenWeatherMapLocation): WeatherUpdateResult<OpenWeatherMapLocation>? {
        TODO("Not yet implemented")
    }

}