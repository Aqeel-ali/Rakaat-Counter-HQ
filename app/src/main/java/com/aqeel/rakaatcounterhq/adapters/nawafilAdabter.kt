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

import com.aqeel.rakaatcounterhq.models.nawafil
import com.aqeel.rakaatcounterhq.ui.PrayerActivity

class nawafilAdabter(val context:Context, val elements:MutableList<nawafil>):RecyclerView.Adapter<nawafilAdabter.nawafilViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): nawafilAdabter.nawafilViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.view_item_daily_nawafil,parent,false)
        return nawafilViewHolder(view)

    }

    override fun onBindViewHolder(holder: nawafilAdabter.nawafilViewHolder, position: Int) {
        holder.name.text = elements[position].name
        holder.image.setImageResource(context.resources.getIdentifier(elements[position].image,"drawable",context.packageName))
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    inner class nawafilViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.nawafil_title)
        val image: ImageView = itemView.findViewById(R.id.nawafil_icon)


        init {
            itemView.setOnClickListener {
                val intent = Intent(context, PrayerActivity::class.java)
                intent.putExtra("prayer",elements[adapterPosition].name)
                intent.putExtra("rakaars",elements[adapterPosition].rakaat)
                intent.putExtra("image",elements[adapterPosition].image)
                context.startActivity(intent)

            }
        }


    }
}