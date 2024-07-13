package com.aqeel.rakaatcounterhq.functions

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class layoutsManager {
//    val layoutManager = FlexboxLayoutManager(this)
//    layoutManager.flexDirection = FlexDirection.ROW
//    layoutManager.flexWrap = FlexWrap.WRAP
//    layoutManager.justifyContent = JustifyContent.FLEX_START
//    mainPrayerRecyclerView.setLayoutManager(layoutManager)
    //create a function to set the layout manager
    fun setLayoutManager(context: Context,recyclerView: RecyclerView, flexDirection: Int, flexWrap: Int, justifyContent: Int){
        var  layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = flexDirection
        layoutManager.flexWrap = flexWrap
        layoutManager.justifyContent = justifyContent
        recyclerView.layoutManager = layoutManager
    }
}