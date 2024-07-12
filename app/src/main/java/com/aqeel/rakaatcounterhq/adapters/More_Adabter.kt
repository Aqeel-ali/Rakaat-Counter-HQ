package com.aqeel.rakaatcounterhq.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.models.MoreCategory


class More_Adabter(val context: Context, val elements:MutableList<MoreCategory>): RecyclerView.Adapter<More_Adabter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): More_Adabter.ViewHolder {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_more,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: More_Adabter.ViewHolder, position: Int) {
        val currentItem=elements[position]
        holder.title.text=currentItem.title
        holder.description.text=currentItem.description
        val image=currentItem.image
        val resId=context.resources.getIdentifier(image,"drawable",context.packageName)
        holder.image.setImageResource(resId)

        //hide dvider for last item
        if(position==elements.size-1){
            holder.itemView.findViewById<View>(R.id.divider_more).visibility=View.GONE
        }


    }

    override fun getItemCount(): Int {
        return elements.size
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView =view.findViewById(R.id.more_title)
        val description: TextView =view.findViewById(R.id.description_more)
        val image: ImageView =view.findViewById(R.id.more_icon)

    }
}