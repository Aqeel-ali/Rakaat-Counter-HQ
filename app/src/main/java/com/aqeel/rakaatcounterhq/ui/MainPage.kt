package com.aqeel.rakaatcounterhq.ui


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.Data.getMainPrayerList
import com.aqeel.rakaatcounterhq.Data.getMoreCategoryList
import com.aqeel.rakaatcounterhq.Data.getSecondaryPrayerList
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.adapters.More_Adabter
import com.aqeel.rakaatcounterhq.adapters.PrayerAdaptor
import com.google.android.flexbox.AlignContent
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class MainPage : AppCompatActivity() {
    private lateinit var mainPrayerRecyclerView: RecyclerView
    private lateinit var secondPrayerRecyclerView: RecyclerView
    private lateinit var moreCategoryRecyclerView: RecyclerView
    private lateinit var mainPrayerAdaptor: PrayerAdaptor
    private lateinit var secondPrayerAdaptor: PrayerAdaptor
    private lateinit var moreCategoryAdaptor: More_Adabter
    private lateinit var showMoreButton:Button
    private lateinit var mainHeader:LinearLayout
    private lateinit var mainPrayersLayout:LinearLayout
    private lateinit var moreCategoryLayout:LinearLayout
    private lateinit var secondaryPrayersLayout:LinearLayout
    private lateinit var toolbar: Toolbar
    private lateinit var helpIcon:ImageButton

    //is expanded
    private var isExpanded:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        toolbar=findViewById(R.id.mytoolbar)
        setSupportActionBar(toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        toolbar.setNavigationOnClickListener {

           if (isExpanded){
               showMoreButton.performClick()
              }else{
                finish()
           }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //first block of design
        mainHeader = findViewById(R.id.main_header)

        //button عرض المزيد من الصلوات
        showMoreButton = findViewById(R.id.show_more_button)
        //main prayers layout الصلوات اليومية
        mainPrayersLayout = findViewById(R.id.main_prayers_layout)
        //secondary prayers layout الصلوات المخصوصة
        secondaryPrayersLayout = findViewById(R.id.secondary_prayers_layout)

        //more layout
        moreCategoryLayout=findViewById(R.id.more_layout)


        //card view and layout to change the gravity
        val main_prayers_card = findViewById<CardView>(R.id.main_prayers_card)
        val main_inner_layout = findViewById<LinearLayout>(R.id.main_prayers_inner_layout)

        helpIcon=findViewById(R.id.help_ic_toolbar)



        ////get imageview from view holder layout

//        val myLayout: View = LayoutInflater.from(this).inflate(R.layout.viewholder_prayer, null)
//
//
//// load the text view
//        var image_icon = myLayout.findViewById<ImageView>(R.id.prayer_icon)
//        var im_title=myLayout.findViewById<TextView>(R.id.title_prayer)




        //click show more button
        showMoreButton.setOnClickListener {
            //animate the main prayers layout going up and header fading out
            if (mainHeader.visibility == LinearLayout.VISIBLE) {
                isExpanded = true


                //toolbar changes
                toolbar.setBackgroundColor(getColor(R.color.white))
                helpIcon.visibility=ImageView.VISIBLE



                val mainPrayersGoUpAnimation = ObjectAnimator.ofFloat(mainPrayersLayout, "translationY", +400f, 0f)
                mainPrayersGoUpAnimation.duration = 500
                mainPrayersGoUpAnimation.interpolator = AccelerateDecelerateInterpolator()
                mainPrayersGoUpAnimation.start()

                //header fade out animation
                val headerFadeOutAnimation = ObjectAnimator.ofFloat(mainHeader, "alpha", 1f, 0f)
                headerFadeOutAnimation.duration = 500
                headerFadeOutAnimation.start()
                mainHeader.visibility = LinearLayout.GONE

                //show the secondary prayers layout and animate
                secondaryPrayersLayout.visibility = LinearLayout.VISIBLE
                val secondaryPrayersGoUpAnimation = ObjectAnimator.ofFloat(secondaryPrayersLayout, "translationY", +400f, 0f)
                secondaryPrayersGoUpAnimation.duration = 500
                secondaryPrayersGoUpAnimation.interpolator = AccelerateDecelerateInterpolator()
                secondaryPrayersGoUpAnimation.start()


                moreCategoryLayout.visibility=LinearLayout.VISIBLE



                //change the button text and icon
                showMoreButton.text = getString(R.string.show_less_buttom)
                showMoreButton.setCompoundDrawablesRelativeWithIntrinsicBounds( 0,0, if (mainHeader.visibility == LinearLayout.GONE) R.drawable.arrow_up else R.drawable.arrow_down,0)



                //hide the card view
                 main_prayers_card.setCardBackgroundColor(AppCompatResources.getColorStateList(this, R.color.backgroundLight))

                //change the gravity of the inner layout
                main_inner_layout.setHorizontalGravity(LinearLayout.TEXT_ALIGNMENT_VIEW_START)


                //change span count of the main prayers recycler view
                val layoutManager = FlexboxLayoutManager(this)
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.flexWrap = FlexWrap.WRAP
                layoutManager.justifyContent = JustifyContent.FLEX_START
                mainPrayerRecyclerView.setLayoutManager(layoutManager)




                //change the background of the prayer_icon ImageView
                mainPrayerAdaptor.changeBackgroundResource(R.drawable.green_cirle_bg ,isExpanded,getColor(R.color.primaryBrandLight))

                secondPrayerAdaptor.changeBackgroundResource(R.drawable.white_cirle_bg, isExpanded,getColor(R.color.tertiaryBlackShadesLight))

                //change the tint color of the prayer_icon ImageView


            } else {
                isExpanded = false



                //toolbar changes
                toolbar.setBackgroundColor(getColor(R.color.backgroundLight))
                helpIcon.visibility=ImageView.GONE



                val mainPrayersGoDownAnimation = ObjectAnimator.ofFloat(mainPrayersLayout, "translationY", -400f, 0f)
                mainPrayersGoDownAnimation.duration = 500
                mainPrayersGoDownAnimation.interpolator = AccelerateDecelerateInterpolator()
                mainPrayersGoDownAnimation.start()


                val headerShowAnimation = ObjectAnimator.ofFloat(mainHeader, "alpha", 0f, 1f)
                headerShowAnimation.duration = 500
                headerShowAnimation.start()
                mainHeader.visibility = LinearLayout.VISIBLE

                // animation don't work !!!
//                val secondaryPrayersGoDownAnimation = ObjectAnimator.ofFloat(secondaryPrayersLayout, "translationY", -400f, 0f)
//                secondaryPrayersGoDownAnimation.duration = 500
//                secondaryPrayersGoDownAnimation.interpolator = AccelerateDecelerateInterpolator()
//                secondaryPrayersGoDownAnimation.start()
                secondaryPrayersLayout.visibility = LinearLayout.GONE

                moreCategoryLayout.visibility=LinearLayout.GONE


                //change the button text and icon
                showMoreButton.text = getString(R.string.show_more_buttom)
                showMoreButton.setCompoundDrawablesRelativeWithIntrinsicBounds( 0,0, if (mainHeader.visibility == LinearLayout.GONE) R.drawable.arrow_up else R.drawable.arrow_down,0)

                //show the card view
                main_prayers_card.setCardBackgroundColor(AppCompatResources.getColorStateList(this, R.color.white))
                main_prayers_card.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(0, 0, 0, 0)
                }
                main_prayers_card.radius=50F
                //make recycler view start
                main_inner_layout.setHorizontalGravity(LinearLayout.TEXT_ALIGNMENT_GRAVITY)


                val layoutManager = FlexboxLayoutManager(this)
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.flexWrap = FlexWrap.WRAP
                layoutManager.justifyContent = JustifyContent.SPACE_EVENLY
                mainPrayerRecyclerView.setLayoutManager(layoutManager)



//change the background of the prayer_icon ImageView
                mainPrayerAdaptor.changeBackgroundResource(R.drawable.gray_cirle_bg,isExpanded,0)

                //change the tint color of the prayer_icon ImageView


            }


        }

        //Help icon click
        helpIcon.setOnClickListener {
            Toast.makeText( this,"Help Icon Click", Toast.LENGTH_SHORT).show()
        }



        val main_prayerList= getMainPrayerList(this)
        val secondary_prayerList= getSecondaryPrayerList(this)
        val moreCategoryList= getMoreCategoryList(this)



        mainPrayerAdaptor = PrayerAdaptor(this, main_prayerList)
        mainPrayerRecyclerView = findViewById(R.id.main_prayers_recycler)
        mainPrayerRecyclerView.adapter = mainPrayerAdaptor
//        mainPrayerRecyclerView.layoutManager = GridLayoutManager(this, 3)

        val mainlayoutManager = FlexboxLayoutManager(this)
        mainlayoutManager.flexDirection = FlexDirection.ROW
        mainlayoutManager.flexWrap = FlexWrap.WRAP
        mainlayoutManager.justifyContent = JustifyContent.SPACE_EVENLY
        mainPrayerRecyclerView.setLayoutManager(mainlayoutManager)
        mainPrayerAdaptor.changeBackgroundResource(R.drawable.gray_cirle_bg,isExpanded,0)



        secondPrayerAdaptor = PrayerAdaptor(this, secondary_prayerList)
        secondPrayerRecyclerView = findViewById(R.id.secondary_prayers_recycler)
        secondPrayerRecyclerView.adapter = secondPrayerAdaptor

        val secondlayoutManager = FlexboxLayoutManager(this)
        secondlayoutManager.flexDirection = FlexDirection.ROW
        secondlayoutManager.flexWrap = FlexWrap.WRAP
        secondlayoutManager.justifyContent = JustifyContent.FLEX_START
        secondPrayerRecyclerView.setLayoutManager(secondlayoutManager)




        moreCategoryAdaptor = More_Adabter(this, moreCategoryList)
        moreCategoryRecyclerView = findViewById(R.id.more_recycler)
        moreCategoryRecyclerView.adapter = moreCategoryAdaptor
        moreCategoryRecyclerView.layoutManager = LinearLayoutManager(this)






    }
}