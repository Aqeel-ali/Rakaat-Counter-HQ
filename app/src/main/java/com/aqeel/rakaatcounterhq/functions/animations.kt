package com.aqeel.rakaatcounterhq.functions

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout

class animations {

    fun  translationAnimation(layout: LinearLayout, translation:String, translationy:Float, translationValuex:Float){
        val animation = ObjectAnimator.ofFloat(layout, translation, translationy, translationValuex)
        animation.duration = 500
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.start()
    }
    fun fadeAnimation(layout: LinearLayout, alpha:Float, alphaValue:Float){
        val animation = ObjectAnimator.ofFloat(layout, "alpha", alpha, alphaValue)
        animation.duration = 500
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.start()
    }

    fun showView(layout: LinearLayout){
        layout.alpha = 0f
        layout.visibility = LinearLayout.VISIBLE
        fadeAnimation(layout, 0f, 1f)
    }

}