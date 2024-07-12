package com.aqeel.rakaatcounterhq.Data

import android.content.Context
import com.aqeel.rakaatcounterhq.models.MoreCategory

fun getMoreCategoryList(context: Context): MutableList<MoreCategory> {
    return mutableListOf(
        MoreCategory(
            "إرشادات عداد الركع",
            "تعلم كيفية استخدام الخدمة عن طريق الإرشادات.",
            "lamp_ic"
        ),
        MoreCategory(
            "البوصلة",
            "تأكد من الاتجاه الصحيح للقبلة قبل البدء بالصلاة.",
            "compass_ic"
        ),
        MoreCategory(
            "أحكام السهو",
            "اقرأ الأحكام لاستعمالها عند السهو في الصلاة.",
            "notes_ic"
        ),
        MoreCategory(
            "باب الصلاة",
            "كيفية الصلاة، روايات عامة حولها، نصائح للخشوع.",
            "q_mark_ic"
        ),
    )}