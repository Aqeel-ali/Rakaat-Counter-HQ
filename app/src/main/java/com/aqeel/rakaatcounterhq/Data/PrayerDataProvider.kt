package com.aqeel.rakaatcounterhq.Data


import com.aqeel.rakaatcounterhq.models.Prayer
import com.aqeel.rakaatcounterhq.R
import android.content.Context

fun getMainPrayerList(context: Context): MutableList<Prayer> {
    return mutableListOf(
        Prayer(
            "${context.getString(R.string.morning_prayer)}",
            2,
            "sun_up"
        ),

        Prayer(
            "${context.getString(R.string.noon_prayer)}",
            4,
            "sun2"
        ),
        Prayer(
            "${context.getString(R.string.afternoon_prayer)}",
            4,
            "sun1"
        ),
        Prayer(
            "${context.getString(R.string.sunset_prayer)}",
            3,
            "sun_down"
        ),
        Prayer(
            "${context.getString(R.string.asha_prayer)}",
            4,
            "moon"

        ),

    )
}

fun getSecondaryPrayerList(context: Context): MutableList<Prayer> {
    return mutableListOf(
        Prayer(
            "${context.getString(R.string.night_prayer)}"
            , 4,
            "moon2"

        ),
        Prayer(
            "${context.getString(R.string.daily_nawafil)}"
            , 4,
            "rug_ic"

        ),
        Prayer(
            "${context.getString(R.string.prayer_of_signs)}"
            , 4,
            "warning"

        ),
        Prayer(
            "${context.getString(R.string.ghufran_prayer)}"
            , 4,
            "rug_ic"

        ),
        Prayer(
            "${context.getString(R.string.friday_prayer)}"
            , 4,
            "friday_ic"

        ),
        Prayer(
            "${context.getString(R.string.eid_prayer)}"
            , 4,
            "eid_ic"

        ),
        Prayer(
            "${context.getString(R.string.visit_prayer)}"
            , 4,
            "dome_ic"

        ),
        Prayer(
            "${context.getString(R.string.manual_customize)}"
            , 4,
            "added_ic"

        ),
        // Add the rest of your prayers here...
    )
}