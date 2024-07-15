package com.aqeel.rakaatcounterhq.ui


import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.Data.getMainPrayerList
import com.aqeel.rakaatcounterhq.Data.getMoreCategoryList
import com.aqeel.rakaatcounterhq.Data.getSecondaryPrayerList
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.adapters.More_Adabter
import com.aqeel.rakaatcounterhq.adapters.PrayerAdaptor
import com.aqeel.rakaatcounterhq.functions.animations
import com.aqeel.rakaatcounterhq.functions.layoutsManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class MainPage : AppCompatActivity() {

    // Views and Adaptors variables
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
    private lateinit var help_button:AppCompatButton
    private lateinit var mainScroll:NestedScrollView

    //use classes from functions folder
   private val myAnimations = animations()
    private val myLayoutsManager = layoutsManager()


    //is expanded
    private var isExpanded:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)


        //toolbar
        toolbar=findViewById(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
           if (isExpanded){
               showMoreButton.performClick()
              }else{
                finish()
           } }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//
//        // Setup gesture detector for swipe up action
        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {

                if (mainScroll.canScrollVertically(1) && velocityY > 0) {
                    showMoreButton.performClick()
                }
                //if scroll view id in the end of the scroll and scroll up then hide the more prayers
                if (!mainScroll.canScrollVertically(-1) && velocityY < 0) {
                    showMoreButton.performClick()
                }

                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

        mainScroll=findViewById(R.id.main_scroll)

        mainScroll.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }


        //instrcutions button
        help_button=findViewById(R.id.help_button)
        help_button.setOnClickListener {
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_instructuins).show(supportFragmentManager, "instructions")
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



        //click show more button
        showMoreButton.setOnClickListener {
            //animate the main prayers
            if (!isExpanded) {
                isExpanded = true
                //toolbar changes
                toolbar.setBackgroundColor(getColor(R.color.white))
                helpIcon.visibility=ImageView.VISIBLE

                myAnimations.translationAnimation(mainPrayersLayout,"translationY",+400f,0f)

                //header fade out animation
                myAnimations.fadeAnimation(mainHeader,1f,0f)
                mainHeader.visibility = LinearLayout.GONE

                //show the secondary prayers layout and animate
                myAnimations.translationAnimation(secondaryPrayersLayout,"translationY",+400f,0f)
                secondaryPrayersLayout.visibility=LinearLayout.VISIBLE

                //show the more category layout
                myAnimations.translationAnimation(moreCategoryLayout,"translationY",+400f,0f)
                moreCategoryLayout.visibility=LinearLayout.VISIBLE



                //change the button text and icon
                showMoreButton.text = getString(R.string.show_less_buttom)
                showMoreButton.setCompoundDrawablesRelativeWithIntrinsicBounds( 0,0, if (mainHeader.visibility == LinearLayout.GONE) R.drawable.arrow_up else R.drawable.arrow_down,0)



                //hide the card view
                 main_prayers_card.setCardBackgroundColor(AppCompatResources.getColorStateList(this, R.color.backgroundLight))

                //change the gravity of the inner layout
                main_inner_layout.setHorizontalGravity(LinearLayout.TEXT_ALIGNMENT_VIEW_START)



                //change the layout manager to start
                myLayoutsManager.setLayoutManager(this,mainPrayerRecyclerView,FlexDirection.ROW,FlexWrap.WRAP,JustifyContent.FLEX_START)



                //change the background of the prayer_icon ImageView
                mainPrayerAdaptor.changeBackgroundResource(R.drawable.green_cirle_bg ,isExpanded,getColor(R.color.primaryBrandLight))

                secondPrayerAdaptor.changeBackgroundResource(R.drawable.white_cirle_bg, isExpanded,getColor(R.color.tertiaryBlackShadesLight))

                //change the tint color of the prayer_icon ImageView


            } else {
                isExpanded = false



                //toolbar changes
                toolbar.setBackgroundColor(getColor(R.color.backgroundLight))
                helpIcon.visibility=ImageView.GONE




                myAnimations.translationAnimation(mainPrayersLayout,"translationY",-400f,0f)



                myAnimations.fadeAnimation(mainHeader,0f,1f)
                mainHeader.visibility = LinearLayout.VISIBLE

                // animation don't work !!!
                myAnimations.translationAnimation(secondaryPrayersLayout,"translationY",-400f,0f)
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


            //set the layout manager to center
           myLayoutsManager.setLayoutManager(this,mainPrayerRecyclerView,FlexDirection.ROW,FlexWrap.WRAP,JustifyContent.SPACE_EVENLY)



//change the background of the prayer_icon ImageView
                mainPrayerAdaptor.changeBackgroundResource(R.drawable.gray_cirle_bg,isExpanded,0)

                //change the tint color of the prayer_icon ImageView


            }


        }

        //Help icon click
        helpIcon.setOnClickListener {
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_instructuins).show(supportFragmentManager, "instructions")
        }


        //get data
        val main_prayerList= getMainPrayerList(this)
        val secondary_prayerList= getSecondaryPrayerList(this)
        val moreCategoryList= getMoreCategoryList(this)


        mainPrayerAdaptor = PrayerAdaptor(this, main_prayerList)
        mainPrayerRecyclerView = findViewById(R.id.main_prayers_recycler)
        mainPrayerRecyclerView.adapter = mainPrayerAdaptor

        //set the layout manager
        myLayoutsManager.setLayoutManager(this,mainPrayerRecyclerView,FlexDirection.ROW,FlexWrap.WRAP,JustifyContent.SPACE_EVENLY)
        mainPrayerAdaptor.changeBackgroundResource(R.drawable.gray_cirle_bg,isExpanded,0)



        secondPrayerAdaptor = PrayerAdaptor(this, secondary_prayerList)
        secondPrayerRecyclerView = findViewById(R.id.secondary_prayers_recycler)
        secondPrayerRecyclerView.adapter = secondPrayerAdaptor
        //set the layout manager
        myLayoutsManager.setLayoutManager(this,secondPrayerRecyclerView,FlexDirection.ROW,FlexWrap.WRAP,JustifyContent.FLEX_START)

        //more category recycler view and set layout manager
        moreCategoryAdaptor = More_Adabter(this, moreCategoryList)
        moreCategoryRecyclerView = findViewById(R.id.more_recycler)
        moreCategoryRecyclerView.adapter = moreCategoryAdaptor
        moreCategoryRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}