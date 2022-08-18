package com.orogersilva.myweatherforecast.data.enum

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.orogersilva.myweatherforecast.data.R
import com.orogersilva.myweatherforecast.ui.theme.Blue10
import com.orogersilva.myweatherforecast.ui.theme.Blue20
import com.orogersilva.myweatherforecast.ui.theme.Blue25
import com.orogersilva.myweatherforecast.ui.theme.Blue30
import com.orogersilva.myweatherforecast.ui.theme.Blue40
import com.orogersilva.myweatherforecast.ui.theme.Blue50
import com.orogersilva.myweatherforecast.ui.theme.Blue60
import com.orogersilva.myweatherforecast.ui.theme.Blue80
import com.orogersilva.myweatherforecast.ui.theme.Blue81
import com.orogersilva.myweatherforecast.ui.theme.Blue82
import com.orogersilva.myweatherforecast.ui.theme.Blue87
import com.orogersilva.myweatherforecast.ui.theme.Blue91
import com.orogersilva.myweatherforecast.ui.theme.Blue92
import com.orogersilva.myweatherforecast.ui.theme.Blue93
import com.orogersilva.myweatherforecast.ui.theme.Blue94
import com.orogersilva.myweatherforecast.ui.theme.Blue95
import com.orogersilva.myweatherforecast.ui.theme.Gray30
import com.orogersilva.myweatherforecast.ui.theme.Gray40
import com.orogersilva.myweatherforecast.ui.theme.Gray50
import com.orogersilva.myweatherforecast.ui.theme.Gray80
import com.orogersilva.myweatherforecast.ui.theme.Green90
import com.orogersilva.myweatherforecast.ui.theme.Purple15
import com.orogersilva.myweatherforecast.ui.theme.Purple30
import com.orogersilva.myweatherforecast.ui.theme.SnowBlue40
import com.orogersilva.myweatherforecast.ui.theme.SnowBlue45
import com.orogersilva.myweatherforecast.ui.theme.SnowBlue50
import com.orogersilva.myweatherforecast.ui.theme.SnowBlue60
import com.orogersilva.myweatherforecast.ui.theme.SnowBlue80

enum class WeatherCode(
    val code: Int,
    val backgroundColor: Color,
    val textColor: Color,
    @StringRes val weatherDescriptionStrId: Int
) {
    CLEAR_SKY(0, Blue40, Color.White, R.string.clear_sky),
    MAINLY_CLEAR(1, Blue50, Color.Black, R.string.mainly_clear),
    PARTY_CLOUDY(2, Blue60, Color.Black, R.string.party_cloudy),
    OVERCAST(3, Blue80, Color.Black, R.string.overcast),
    FOG(45, Gray80, Color.Black, R.string.fog),
    RIME_FOG(48, Gray50, Color.Black, R.string.rime_fog),
    LIGHT_DRIZZLE(51, Blue95, Color.Black, R.string.light_drizzle),
    MODERATE_DRIZZLE(53, Blue94, Color.Black, R.string.moderate_drizzle),
    DENSE_DRIZZLE(55, Blue93, Color.Black, R.string.dense_drizzle),
    LIGHT_FREEZING_DRIZZLE(56, Blue92, Color.Black, R.string.light_freezing_drizzle),
    DENSE_FREEZING_DRIZZLE(57, Blue91, Color.White, R.string.dense_freezing_drizzle),
    SLIGHT_RAIN(61, Blue87, Color.White, R.string.slight_rain),
    MODERATE_RAIN(63, Blue82, Color.White, R.string.moderate_rain),
    HEAVY_RAIN(65, Blue81, Color.White, R.string.heavy_rain),
    LIGHT_FREEZING_RAIN(66, Purple30, Color.White, R.string.light_freezing_rain),
    HEAVY_FREEZING_RAIN(67, Purple15, Color.White, R.string.heavy_freezing_rain),
    SLIGHT_SNOW_FALL(71, SnowBlue60, Color.White, R.string.slight_snow_fall),
    MODERATE_SNOW_FALL(73, SnowBlue50, Color.White, R.string.moderate_snow_fall),
    HEAVY_SNOW_FALL(75, SnowBlue40, Color.White, R.string.heavy_snow_fall),
    SNOW_GRAINS(77, Green90, Color.Black, R.string.snow_grains),
    SLIGHT_RAIN_SHOWERS(80, Blue30, Color.White, R.string.slight_rain_showers),
    MODERATE_RAIN_SHOWERS(81, Blue25, Color.White, R.string.moderate_rain_showers),
    VIOLENT_RAIN_SHOWERS(82, Blue20, Color.White, R.string.violent_rain_showers),
    SLIGHT_SNOW_SHOWERS(85, SnowBlue80, Color.White, R.string.slight_snow_showers),
    HEAVY_SNOW_SHOWERS(86, SnowBlue45, Color.White, R.string.heavy_snow_showers),
    SLIGHT_OR_MODERATE_THUNDERSTORM(
        95, Blue10,
        Color.White, R.string.slight_or_moderate_thunderstorm
    ),
    THUNDERSTORM_WITH_SLIGHT_HAIL(
        96, Gray40, Color.White,
        R.string.thunderstorm_with_slight_hail
    ),
    THUNDERSTORM_WITH_HEAVY_HAIL(
        99, Gray30, Color.White,
        R.string.thunderstorm_with_heavy_hail
    ),
    UNKNOWN(Int.MAX_VALUE, Color.Black, Color.White, R.string.unknown);

    companion object {
        fun valueOf(code: Int): WeatherCode = values().find { it.code == code } ?: UNKNOWN
    }
}
