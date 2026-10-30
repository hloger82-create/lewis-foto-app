package com.lewisfoto.app.util

import com.lewisfoto.app.data.DailyWeather
import com.lewisfoto.app.data.HourlyWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object WeatherService {

    private const val BASE = "https://api.open-meteo.com/v1/forecast"

    suspend fun fetchForecast(lat: Double, lon: Double, days: Int = 7): Result<Pair<List<DailyWeather>, List<HourlyWeather>>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val url = URL(
                    "$BASE?latitude=$lat&longitude=$lon" +
                        "&daily=sunrise,sunset,temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max,cloud_cover_mean" +
                        "&hourly=temperature_2m,cloud_cover,precipitation,wind_speed_10m,weather_code" +
                        "&forecast_days=$days&timezone=Europe%2FLondon"
                )
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 10000
                conn.readTimeout = 10000
                val json = JSONObject(conn.inputStream.bufferedReader().readText())
                val zone = ZoneId.of("Europe/London")

                val dailyArr = json.getJSONObject("daily")
                val dTimes = dailyArr.getJSONArray("time")
                val dSunrise = dailyArr.getJSONArray("sunrise")
                val dSunset = dailyArr.getJSONArray("sunset")
                val dTMax = dailyArr.getJSONArray("temperature_2m_max")
                val dTMin = dailyArr.getJSONArray("temperature_2m_min")
                val dPrecip = dailyArr.getJSONArray("precipitation_sum")
                val dWind = dailyArr.getJSONArray("wind_speed_10m_max")
                val dCloud = dailyArr.getJSONArray("cloud_cover_mean")
                val dailies = (0 until dTimes.length()).map { i ->
                    DailyWeather(
                        date = LocalDate.parse(dTimes.getString(i)),
                        sunrise = LocalDateTime.parse(dSunrise.getString(i)).atZone(zone).toLocalDateTime(),
                        sunset = LocalDateTime.parse(dSunset.getString(i)).atZone(zone).toLocalDateTime(),
                        tempMinC = dTMin.getDouble(i),
                        tempMaxC = dTMax.getDouble(i),
                        precipitationMm = dPrecip.getDouble(i),
                        maxWindKmh = dWind.getDouble(i),
                        cloudCoverMeanPct = dCloud.getInt(i)
                    )
                }

                val hourly = json.getJSONObject("hourly")
                val hTimes = hourly.getJSONArray("time")
                val hTemp = hourly.getJSONArray("temperature_2m")
                val hCloud = hourly.getJSONArray("cloud_cover")
                val hPrecip = hourly.getJSONArray("precipitation")
                val hWind = hourly.getJSONArray("wind_speed_10m")
                val hCode = hourly.getJSONArray("weather_code")
                val now = LocalDateTime.now()
                val hours = (0 until hTimes.length()).mapNotNull { i ->
                    val t = LocalDateTime.parse(hTimes.getString(i))
                    if (t.isBefore(now.minusHours(1))) null
                    else HourlyWeather(
                        time = t,
                        temperatureC = hTemp.getDouble(i),
                        cloudCoverPct = hCloud.getInt(i),
                        precipitationMm = hPrecip.getDouble(i),
                        windSpeedKmh = hWind.getDouble(i),
                        weatherCode = hCode.getInt(i)
                    )
                }.take(48)

                dailies to hours
            }
        }

    suspend fun rainViewerLatestTimestamp(): Result<Long> = withContext(Dispatchers.IO) {
        runCatching {
            val conn = URL("https://api.rainviewer.com/public/weather-maps-v3.json").openConnection() as HttpURLConnection
            conn.connectTimeout = 8000
            conn.readTimeout = 8000
            val json = JSONObject(conn.inputStream.bufferedReader().readText())
            val frames: JSONArray = json.getJSONObject("radar").getJSONArray("past")
            val last = frames.getJSONObject(frames.length() - 1)
            last.getLong("time")
        }
    }
}
