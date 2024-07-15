package com.aqeel.rakaatcounterhq.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.models.Instructions


class  InstructionsAdabter(private val context: Context, private val instructionsList: MutableList<Instructions>) : RecyclerView.Adapter<InstructionsAdabter.InstructionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_instructuins, parent, false)
        return InstructionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        val currentItem = instructionsList[position]
        holder.description.text = currentItem.description
        val image = currentItem.image
        val resId = context.resources.getIdentifier(image, "drawable", context.packageName)
        holder.image.setImageResource(resId)
    }

    override fun getItemCount(): Int {
        return instructionsList.size
    }

    inner class InstructionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var description: TextView = itemView.findViewById(R.id.instructions_description)
         var image: ImageView = itemView.findViewById(R.id.instructions_icon)

    }
}