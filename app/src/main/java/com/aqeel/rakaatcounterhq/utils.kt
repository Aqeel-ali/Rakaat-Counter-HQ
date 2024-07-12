package com.aqeel.rakaatcounterhq

import android.content.res.Resources


object utils {
    fun convertDpToPixel(dp: Int): Int {
        var dpf=dp.toFloat()
        val metrics = Resources.getSystem().displayMetrics
        val px = dpf * (metrics.densityDpi / 160f)
        return Math.round(px)
    }
}