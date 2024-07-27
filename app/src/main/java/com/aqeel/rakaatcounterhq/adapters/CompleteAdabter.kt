package com.aqeel.rakaatcounterhq.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.Data.getMainPrayerList
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.models.Prayer
import com.aqeel.rakaatcounterhq.ui.PrayerActivity

var prayerName: String = ""

class CompleteAdabter(val context:Context, val elements:MutableList<String>,prayer:String):RecyclerView.Adapter<CompleteAdabter.CompleteViewHolder>(){
    //set the prayer name
    init {
        prayerName = prayer
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompleteAdabter.CompleteViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.viewholder_complet,parent,false)
        return CompleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompleteAdabter.CompleteViewHolder, position: Int) {
        holder.title.text = elements[position]

    }

    override fun getItemCount(): Int {
        return elements.size
    }

    inner class CompleteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.complete_title)


        init {
            itemView.setOnClickListener {
                when(adapterPosition){
                    0 -> {
                        Toast.makeText(context, "الانتقال إلى تسبيح الزهراء ع", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        Toast.makeText(context, "الانتقال إلى تعقيبات الصلاة", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        if (prayerName.isNotEmpty()) {

                            val prayerList = getMainPrayerList(context)

                            //find index of the prayer from getMainPrayerList
                            val index:Int =prayerList.indexOfFirst { it.name == prayerName }


                            val prayer = prayerList[index+1]

                            val intent=Intent(context, PrayerActivity::class.java)
                            intent.putExtra("prayer",prayer.name)
                            intent.putExtra("image",prayer.image)
                            intent.putExtra("rakaars",prayer.rakaats)
                            context.startActivity(intent)
                        }
                    }
                }

            }
        }

    }
}