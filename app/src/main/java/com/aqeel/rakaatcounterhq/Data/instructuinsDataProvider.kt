package com.aqeel.rakaatcounterhq.Data

import android.content.Context
import com.aqeel.rakaatcounterhq.models.Instructions

fun getInstructionsList(): MutableList<Instructions> {
    return mutableListOf(
        Instructions(
            "حدد الصلاة من الصلوات اليومية أو المخصوصة أو حدد عدد الركعات بشكل يدوي.",
            "gps_ic"
        ),
        Instructions(
            "ضع الهاتف أسفل التربة قرب أنفك عند السجود، وسيتم احتساب الركعات والسجدات تلقائيا عن طريق المستشعر في الهاتف.",
            "sensor_ic"
        ),
        Instructions(
            "في حال كان هاتفك لا يدعم المستشعر، يمكنك استعمال اللمس، بأن تلمس الهاتف بأنفك عند السجود، فيبدأ بالعد.",
            "touch_ic"

        ),
        Instructions(
            "لرؤية المقطع الإرشادي الكامل اضغط هنا.",
            "play_ic"
        )

    )}
