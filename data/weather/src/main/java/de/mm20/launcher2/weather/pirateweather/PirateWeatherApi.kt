package de.mm20.launcher2.weather.pirateweather

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class ForecastResponse (
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val offset: Long,
    val elevation: Long,
    val currently: Currently,
    val minutely: Minutely,
    val hourly: Hourly,
    val daily: Daily,
    val alerts: List<Alert>,
    val flags: Flags
)

data class Alert(
    val title: String,
    val regions: List<String>,
    val severity: String,
    val time: Int,
    val expires: Int,
    val description: String,
    val uri: String
)

data class Currently (
    val time: Long,
    val summary: String,
    val icon: String,
    val nearestStormDistance: Long? = null,
    val nearestStormBearing: Long? = null,
    val precipIntensity: Double,
    val precipProbability: Long,
    val precipIntensityError: Long,
    val precipType: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windBearing: Long,
    val cloudCover: Double,
    val uvIndex: Double,
    val visibility: Double,
    val ozone: Double,
    val precipAccumulation: Double? = null
)

data class Daily (
    val summary: String,
    val icon: String,
    val data: List<DailyDatum>
)

data class DailyDatum (
    val time: Long,
    val icon: String,
    val summary: String,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val moonPhase: Double,
    val precipIntensity: Double,
    val precipIntensityMax: Double,
    val precipIntensityMaxTime: Long,
    val precipProbability: Double,
    val precipAccumulation: Double,
    val precipType: String,
    val temperatureHigh: Double,
    val temperatureHighTime: Long,
    val temperatureLow: Double,
    val temperatureLowTime: Long,
    val apparentTemperatureHigh: Double,
    val apparentTemperatureHighTime: Long,
    val apparentTemperatureLow: Double,
    val apparentTemperatureLowTime: Long,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windGustTime: Long,
    val windBearing: Long,
    val cloudCover: Double,
    val uvIndex: Double,
    val uvIndexTime: Long,
    val visibility: Long,
    val temperatureMin: Double,
    val temperatureMinTime: Long,
    val temperatureMax: Double,
    val temperatureMaxTime: Long,
    val apparentTemperatureMin: Double,
    val apparentTemperatureMinTime: Long,
    val apparentTemperatureMax: Double,
    val apparentTemperatureMaxTime: Long
)

data class Flags (
    val sources: List<String>,
    val sourceTimes: SourceTimes,
    val nearestStation: Long,
    val units: String,
    val version: String
)

data class SourceTimes (
    val hrrr018: String,
    val hrrrSubh: String,
    val hrrr1848: String,
    val gfs: String,
    val gefs: String
)

data class Hourly (
    val summary: String,
    val icon: String,
    val data: List<Currently>
)

data class Minutely (
    val summary: String,
    val icon: String,
    val data: List<MinutelyDatum>
)

data class MinutelyDatum (
    val time: Long,
    val precipIntensity: Long,
    val precipProbability: Long,
    val precipIntensityError: Long,
    val precipType: String
)

interface PirateWeatherApi {
    @GET("/forecast/{apiKey}/{latLng}")
    suspend fun getForecast(
        @Path("apiKey") apiKey: String,
        @Path("latLng") latLng: String,
        @Query("units") units: String,
        @Query("exclude") exclude: String? = null,
        @Query("extend") extend: Boolean? = null,
        @Query("lang") language: String? = null, // apparently this isn't working yet
    ): ForecastResponse
}