package com.aqeel.rakaatcounterhq.adapters



import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.models.Prayer
import com.aqeel.rakaatcounterhq.ui.PrayerActivity
import com.aqeel.rakaatcounterhq.utils


class PrayerAdaptor(val context:Context, val elements:MutableList<Prayer>):RecyclerView.Adapter<PrayerAdaptor.PrayerViewHolder>(){

    var BackgroundColor : Int = 0
    var isExpand: Boolean = false
    var tintColor: Int = 0


    // val to store the original values

        // original width and height
        var originalWidth: Int = utils.convertDpToPixel(65)
        var originalHeight: Int = utils.convertDpToPixel(65)
        // original padding
        var originalPadding: Int = utils.convertDpToPixel(18)
        // original text size
        var originalTextSize: Float = 14f
        // original end margin
        var originalEndMargin: Int = utils.convertDpToPixel(12)



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrayerAdaptor.PrayerViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.viewholder_prayer,parent,false)
        return PrayerViewHolder(view)

    }
    //change the background
    fun changeBackgroundResource (bColor: Int,isE : Boolean, tintColor: Int) {
        // Notify the adapter that the data has changed
        BackgroundColor = bColor
        isExpand = isE
        this.tintColor = tintColor
        notifyDataSetChanged()


    }



    override fun onBindViewHolder(holder: PrayerAdaptor.PrayerViewHolder, position: Int) {
        val currntItem=elements[position]
        holder.title.text=currntItem.name
        val id=context.resources.getIdentifier(currntItem.image,"drawable",context.packageName)
        holder.image.setImageResource(id)



        if (isExpand) {
            //save background id
            holder.image.setBackgroundResource(BackgroundColor)
            holder.image.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

            //change width and height
            holder.image.layoutParams.width = utils.convertDpToPixel(60)
            holder.image.layoutParams.height = utils.convertDpToPixel(60)

            //change padding
            holder.image.setPadding(utils.convertDpToPixel(12), utils.convertDpToPixel(12),utils.convertDpToPixel(12),utils.convertDpToPixel(12))

            //change text size
            holder.title.textSize = 11f

            //set end margin
            val params = holder.prayer_holder.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, utils.convertDpToPixel(28), utils.convertDpToPixel(20), 0)
            holder.prayer_holder.layoutParams = params
        }
        else {
            //restore background id
            holder.image.setBackgroundResource(BackgroundColor)
            holder.image.clearColorFilter()
            //restore width and height
            holder.image.layoutParams.width = originalWidth
            holder.image.layoutParams.height = originalHeight

            //restore padding
            holder.image.setPadding(originalPadding, originalPadding, originalPadding, originalPadding)

            //restore text size
            holder.title.textSize = originalTextSize

            //restore end margin
            val params = holder.prayer_holder.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(originalEndMargin, utils.convertDpToPixel(28), originalEndMargin, 0)
            holder.prayer_holder.layoutParams = params

        }

    }


    override fun getItemCount(): Int {
        return elements.size
    }



    inner class PrayerViewHolder(view: View):RecyclerView.ViewHolder(view){

        val title:TextView=view.findViewById(R.id.title_prayer)
        val image:ImageView=view.findViewById(R.id.prayer_icon)
        val prayer_holder:View=view.findViewById(R.id.prayer_holder)

        init {
            view.setOnClickListener {
                   //go to the prayer activity
                val intent=Intent(context,PrayerActivity::class.java)
                intent.putExtra("prayer",elements[adapterPosition].name)
                intent.putExtra("image",elements[adapterPosition].image)
                intent.putExtra("rakaars",elements[adapterPosition].rakaats)
                context.startActivity(intent)

        }

    }
}
}