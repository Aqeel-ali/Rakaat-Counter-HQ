package com.aqeel.rakaatcounterhq.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aqeel.rakaatcounterhq.R
import com.google.android.material.button.MaterialButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class PrayerActivity : AppCompatActivity() {
    //main var
    private lateinit var prayerName: String
    private  var prayerRakaats : Int = 0
    private lateinit var prayerImage :String
    var Pause = false


    //sensor var
    private lateinit var sensorManager: SensorManager
     lateinit var proximitySensor: Sensor

    // Existing variables
    private var sajjadaCount = 0
    private var currentRakaat = 0


    //view var
    private lateinit var prayerTitle: TextView
    private  lateinit var rakaatsProgressBar: CircularProgressBar
    private lateinit var sajjadaProgressBar: CircularProgressBar
    private lateinit var toolbar: Toolbar
    private lateinit var prayerIcon: ImageView
    private lateinit var settings_icon: ImageView
    private lateinit var help_icon: ImageView
    private lateinit var pause_button: MaterialButton


    //text view
    private lateinit var rakaatsText: TextView
    private lateinit var sajjadaText: TextView


   private var incrementByClick : Boolean = false

    fun updateIncrementByClick(value: Boolean) {
        incrementByClick = value
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prayer)





        //shared pref
        var sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
         incrementByClick = sharedPref.getBoolean("IncrementByClick", false)


        // Get the Sensor Service
        sensorManager =getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!

        if(proximitySensor==null){
            Toast.makeText(this,"No Proximity Sensor Found",Toast.LENGTH_SHORT).show()
            finish()
        }else
        {
            sensorManager.registerListener(proximitySensorListener,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL )
        }

        // incerement by click
        var mainview=findViewById<View>(R.id.main)
        mainview.setOnClickListener {
            if(incrementByClick){
                updateUI()
            }
        }




        toolbar=findViewById(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        toolbar.setNavigationOnClickListener {
                finish()
        }
        settings_icon=findViewById(R.id.settings_ic_toolbar)
        help_icon=findViewById(R.id.help_ic_toolbar)

        //visibility of icons
        settings_icon.visibility=View.VISIBLE
        help_icon.visibility=View.VISIBLE

        settings_icon.setOnClickListener {
            //show bottom sheet
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_settings).show(supportFragmentManager, "settings")


        }
        help_icon.setOnClickListener {
            //show bottom sheet
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_instructuins).show(supportFragmentManager, "instructions")
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    prayerName= intent.getStringExtra("prayer")!!
    prayerRakaats = intent.getIntExtra("rakaars",4)
    prayerImage = intent.getStringExtra("image")!!


        //NEW
        var pause_button=findViewById<MaterialButton>(R.id.pause_button)


        pause_button.setOnClickListener {

        }

        //init views
        prayerTitle=findViewById(R.id.title_prayer)
        rakaatsProgressBar=findViewById(R.id.RakaatProgress)
        prayerIcon=findViewById(R.id.prayer_icon)
        rakaatsText=findViewById(R.id.rakaatText)
        sajjadaText=findViewById(R.id.sajjadaText)

        //set data
        prayerTitle.text=prayerName
        prayerIcon.setImageResource(resources.getIdentifier(prayerImage,"drawable",packageName))
        rakaatsText.text=currentRakaat.toString()
        rakaatsProgressBar.apply {
            setProgressWithAnimation(0f,1000)
            progressMax=prayerRakaats.toFloat()
        }

        sajjadaProgressBar=findViewById(R.id.sajjadaProgress)
        sajjadaText.text=sajjadaCount.toString()
        sajjadaProgressBar.apply {
            setProgressWithAnimation(0f,1000)
            progressMax=2f
        }

        pause_button=findViewById(R.id.pause_button)

        pause_button.setOnClickListener {
            if(Pause){
                pause_button.text="إيقاف مؤقت"
                pause_button.icon = getDrawable(R.drawable.hand)
                pause_button.iconTint = resources.getColorStateList(R.color.primaryBrandLight)
                pause_button.backgroundTintList = resources.getColorStateList(R.color.secondaryBrandLight)
                pause_button.setTextColor(resources.getColor(R.color.primaryBrandLight))
                Pause=false
            }else{
                pause_button.text="استمرار"
                pause_button.icon = getDrawable(R.drawable.playwhite_ic)
                pause_button.iconTint = resources.getColorStateList(R.color.secondaryBrandLight)
                pause_button.backgroundTintList = resources.getColorStateList(R.color.primaryBrandLight)
                pause_button.setTextColor(resources.getColor(R.color.white))
                Pause=true
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(proximitySensorListener)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(proximitySensorListener)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(proximitySensorListener,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL )
    }

    var isCooldownActive = false
    private fun updateUI() {
        if (Pause) {
            Toast.makeText(this, "تم ايقاف العداد مؤقتاً", Toast.LENGTH_SHORT).show()
            return
        }
        if (isCooldownActive) return

        isCooldownActive = true
        Handler(Looper.getMainLooper()).postDelayed({ isCooldownActive = false }, 5000) // 5 seconds cooldown

        if (currentRakaat==0){
            currentRakaat++
            rakaatsText.text=currentRakaat.toString()
            rakaatsProgressBar.setProgressWithAnimation(currentRakaat.toFloat(),1000)
        }
        sajjadaCount++
        sajjadaText.text=sajjadaCount.toString()
        sajjadaProgressBar.setProgressWithAnimation(sajjadaCount.toFloat(),1000)
        if(sajjadaCount==2){
            currentRakaat++

            Handler(Looper.getMainLooper()).postDelayed({
                sajjadaCount = 0
                sajjadaProgressBar.setProgressWithAnimation(sajjadaCount.toFloat(), 1000)
                sajjadaText.text = sajjadaCount.toString()
                rakaatsText.text=currentRakaat.toString()
                rakaatsProgressBar.setProgressWithAnimation(currentRakaat.toFloat(),1000)
            }, 1000) // Delay matches the animation duration

            if(currentRakaat==prayerRakaats+1){
                Toast.makeText(this@PrayerActivity,"Prayer Completed",Toast.LENGTH_SHORT).show()
                currentRakaat=0
                sajjadaCount=0
                rakaatsProgressBar.setProgressWithAnimation(currentRakaat.toFloat(),1000)
                sajjadaProgressBar.setProgressWithAnimation(sajjadaCount.toFloat(),1000)
                rakaatsText.text=currentRakaat.toString()
                sajjadaText.text=sajjadaCount.toString()
            }
        }
    }


    var  proximitySensorListener: SensorEventListener = object :SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
          if(event.sensor.type==Sensor.TYPE_PROXIMITY){
                  if(event.values[0]<proximitySensor.maximumRange){
                      updateUI()
                  }
              }
          }
        }



        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }





    }



}