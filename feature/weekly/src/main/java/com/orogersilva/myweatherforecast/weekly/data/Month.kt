package com.orogersilva.myweatherforecast.weekly.data

import androidx.annotation.StringRes
import com.orogersilva.myweatherforecast.weekly.R

enum class Month(
    val monthValue: Int?,
    @StringRes val monthNameRef: Int?
) {
    JANUARY(1, R.string.january),
    FEBRUARY(2, R.string.february),
    MARCH(3, R.string.march),
    APRIL(4, R.string.april),
    MAY(5, R.string.may),
    JUNE(6, R.string.june),
    JULY(7, R.string.july),
    AUGUST(8, R.string.august),
    SEPTEMBER(9, R.string.september),
    OCTOBER(10, R.string.october),
    NOVEMBER(11, R.string.november),
    DECEMBER(12, R.string.december),
    UNKNOWN(null, null);

    companion object {
        fun valueOf(monthValue: Int): Month = values()
            .find { it.monthValue == monthValue } ?: UNKNOWN
    }
}
