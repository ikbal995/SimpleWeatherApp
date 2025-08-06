import com.example.data.ui.WeatherInfoModel
import com.example.data.ui.WeatherModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "name")
    val cityName: String,

    @Json(name = "main")
    val main: MainInfo,

    @Json(name = "weather")
    val weather: List<WeatherInfo>
)

@JsonClass(generateAdapter = true)
data class MainInfo(
    @Json(name = "temp")
    val temp: Double
)

@JsonClass(generateAdapter = true)
data class WeatherInfo(
    @Json(name = "main")
    val mainCondition: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "icon")
    val icon: String
)


fun WeatherResponse.toUiModel(): WeatherModel = WeatherModel(
    cityName = cityName,
    weatherInfo = weather.map { it.toUiModel() },
    temperature = main.temp
)

fun WeatherInfo.toUiModel(): WeatherInfoModel = WeatherInfoModel(
    mainCondition = mainCondition,
    description = description,
    icon = icon
)