package com.aqeel.rakaatcounterhq.Data


import android.content.Context
import com.aqeel.rakaatcounterhq.models.nawafil

fun getNawafilList(): MutableList<nawafil> {
    return mutableListOf(
        nawafil(
            "نافلة الفجر",
            2,
            "sun_up"
        ),
        nawafil(
            "نافلة الظهر",
            8,
            "sun2"
        ),
        nawafil(
            "نافلة العصر",
            8,
            "sun1"
        ),
        nawafil(
            "نافلة المغرب",
            4,
            "sun_down"
        ),
        nawafil(
            "نافلة العشاء",
            2,
            "moon"
        ),
        nawafil(
            "نافلة الليل",
            8,
            "moon2"
        ),
    )}