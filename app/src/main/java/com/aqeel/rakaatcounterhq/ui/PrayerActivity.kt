package com.aqeel.rakaatcounterhq.ui

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.fragments.CompleteFragment
import com.aqeel.rakaatcounterhq.fragments.tashahudFragment
import com.aqeel.rakaatcounterhq.functions.animations
import com.aqeel.rakaatcounterhq.ui.Dialog.ViewDialog
import com.google.android.material.button.MaterialButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class PrayerActivity : AppCompatActivity() {
    //main var
    private lateinit var prayerName: String
    private var prayerRakaats: Int = 0
    private lateinit var prayerImage: String
    var Pause = false



    //Timer
    private var startTime: Long = 0


    //sensor var
    private lateinit var sensorManager: SensorManager
    lateinit var proximitySensor: Sensor

    // Existing variables
    private var sajjadaCount = 0
    private var currentRakaat = 0


    //view var
    private lateinit var prayerTitle: TextView
    private lateinit var rakaatsProgressBar: CircularProgressBar
    private lateinit var sajjadaProgressBar: CircularProgressBar
    private lateinit var toolbar: Toolbar
    private lateinit var prayerIcon: ImageView
    private lateinit var settings_icon: ImageView
    private lateinit var help_icon: ImageView
    private lateinit var pause_button: MaterialButton
    private lateinit var sajjadaLayout: LinearLayout
    private lateinit var rakkatLayout: LinearLayout
    private lateinit var fragment_container: View


    //text view
    private lateinit var rakaatsText: TextView
    private lateinit var sajjadaText: TextView


    private var incrementByClick: Boolean = false

    fun updateIncrementByClick(value: Boolean) {
        incrementByClick = value
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prayer)


        startTime = System.currentTimeMillis()


        //shared pref
        var sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        incrementByClick = sharedPref.getBoolean("IncrementByClick", false)


        // Get the Sensor Service
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!

        if (proximitySensor == null) {
            Toast.makeText(this, "No Proximity Sensor Found", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            sensorManager.registerListener(
                proximitySensorListener,
                proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        // incerement by click
        var mainview = findViewById<View>(R.id.main)
        mainview.setOnClickListener {
            if (incrementByClick) {
                updateUI()
            }
        }




        toolbar = findViewById(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        toolbar.setNavigationOnClickListener {
            finish()
        }
        settings_icon = findViewById(R.id.settings_ic_toolbar)
        help_icon = findViewById(R.id.help_ic_toolbar)

        //visibility of icons
        settings_icon.visibility = View.VISIBLE
        help_icon.visibility = View.VISIBLE

        settings_icon.setOnClickListener {
            //show bottom sheet
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_settings)
                .show(supportFragmentManager, "settings")


        }
        help_icon.setOnClickListener {
            //show bottom sheet
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_instructuins)
                .show(supportFragmentManager, "instructions")
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prayerName = intent.getStringExtra("prayer")!!
        prayerRakaats = intent.getIntExtra("rakaars", 4)
        prayerImage = intent.getStringExtra("image")!!


        //NEW
        var pause_button = findViewById<MaterialButton>(R.id.pause_button)


        pause_button.setOnClickListener {

        }

        //init views
        prayerTitle = findViewById(R.id.title_prayer)
        rakaatsProgressBar = findViewById(R.id.RakaatProgress)
        prayerIcon = findViewById(R.id.prayer_icon)
        rakaatsText = findViewById(R.id.rakaatText)
        sajjadaText = findViewById(R.id.sajjadaText)
        sajjadaLayout = findViewById(R.id.sajjadaLayout)
        rakkatLayout = findViewById(R.id.rakaatLayout)
        fragment_container = findViewById(R.id.fragment_container)

        //set data
        prayerTitle.text = prayerName
        prayerIcon.setImageResource(resources.getIdentifier(prayerImage, "drawable", packageName))
        rakaatsText.text = currentRakaat.toString()
        rakaatsProgressBar.apply {
            setProgressWithAnimation(0f, 1000)
            progressMax = prayerRakaats.toFloat()
        }

        sajjadaProgressBar = findViewById(R.id.sajjadaProgress)
        sajjadaText.text = sajjadaCount.toString()
        sajjadaProgressBar.apply {
            setProgressWithAnimation(0f, 1000)
            progressMax = 2f
        }

        pause_button = findViewById(R.id.pause_button)

        pause_button.setOnClickListener {
            if (Pause) {
                pause_button.text = "إيقاف مؤقت"
                pause_button.icon = getDrawable(R.drawable.hand)
                pause_button.iconTint = resources.getColorStateList(R.color.primaryBrandLight)
                pause_button.backgroundTintList =
                    resources.getColorStateList(R.color.secondaryBrandLight)
                pause_button.setTextColor(resources.getColor(R.color.primaryBrandLight))
                Pause = false
            } else {
                pause_button.text = "استمرار"
                pause_button.icon = getDrawable(R.drawable.playwhite_ic)
                pause_button.iconTint = resources.getColorStateList(R.color.secondaryBrandLight)
                pause_button.backgroundTintList =
                    resources.getColorStateList(R.color.primaryBrandLight)
                pause_button.setTextColor(resources.getColor(R.color.white))
                Pause = true
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
        sensorManager.registerListener(
            proximitySensorListener,
            proximitySensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    var isCooldownActive = false
    var isTashahudActive = false
    var myanimation = animations()

    private fun updateUI() {
        if (Pause) {
            Toast.makeText(this, "تم ايقاف العداد مؤقتاً", Toast.LENGTH_SHORT).show()
            return
        }
        if (isCooldownActive||isTashahudActive) return

        isCooldownActive = true
        Handler(Looper.getMainLooper()).postDelayed(
            { isCooldownActive = false },
            2000
        ) // 2 seconds cooldown

        if (currentRakaat == 0) {
            currentRakaat++
            rakaatsText.text = currentRakaat.toString()
            rakaatsProgressBar.setProgressWithAnimation(currentRakaat.toFloat(), 1000)
        }

        sajjadaCount++
        sajjadaText.text = sajjadaCount.toString()
        sajjadaProgressBar.setProgressWithAnimation(sajjadaCount.toFloat(), 1000)



        if (sajjadaCount == 1) {
            myanimation.translationAnimation(rakkatLayout, "translationY", 200f, 0f)
            myanimation.showView(sajjadaLayout)
        }
        if (sajjadaCount == 2) {
            currentRakaat++
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    sajjadaCount = 0
                    sajjadaProgressBar.setProgressWithAnimation(sajjadaCount.toFloat(), 1000)
                    sajjadaText.text = sajjadaCount.toString()
                    rakaatsText.text = currentRakaat.toString()
                    rakaatsProgressBar.setProgressWithAnimation(currentRakaat.toFloat(), 1000)

                    myanimation.translationAnimation(rakkatLayout, "translationY", 0f, 200f)
                    myanimation.fadeAnimation(sajjadaLayout, 1f, 0f)
                },
                1000
            ) // Delay matches the animation duration

            if (currentRakaat == prayerRakaats + 1) {

                val elapsedTimeSecond = (System.currentTimeMillis() - startTime)/1000
                //divide prayer rakaats
                val prayerTime = elapsedTimeSecond / prayerRakaats

                if(prayerTime < 30){
                    val alert = ViewDialog()
                    alert.showResetPasswordDialog(this,supportFragmentManager)
                    resetPrayer()
                    return
                }


                showTashahudFragment(
                    "التشهد و التسليم",
                    "أشهد ألا إله إلا الله وحده لا شريك له، وأشهد أن محمّدا عبده ورسوله. اللهم صلّ على محمّد وآل محمّد.\n" +
                            "السلام عليك أيها النبي ورحمة الله وبركاته. السلام علينا وعلى عباد الله الصالحين. السلام عليكم ورحمة الله وبركاته."
                )

                Handler(Looper.getMainLooper()).postDelayed({
                    showCompleteFragment(prayerName)
                }, 18000) // Delay matches the animation duration


                // reset everything
                resetPrayer()





            } else if (currentRakaat == 3) {
                    isTashahudActive = true
                    showTashahudFragment(
                        "التشهد",
                        "أشهد ألا إله إلا الله وحده لا شريك له، وأشهد أن محمّدا عبده ورسوله. اللهم صلّ على محمّد وآل محمّد."
                    )
                Handler(Looper.getMainLooper()).postDelayed({
                    hideTashahudFragment()
                    isTashahudActive = false
                }, 10000) // 10 seconds

            }
        }
    }

    fun resetPrayer(){
        currentRakaat = 0
        sajjadaCount = 0
        rakaatsProgressBar.setProgressWithAnimation(currentRakaat.toFloat(), 1000)
        sajjadaProgressBar.setProgressWithAnimation(sajjadaCount.toFloat(), 1000)
        rakaatsText.text = currentRakaat.toString()
        sajjadaText.text = sajjadaCount.toString()
    }


    fun showTashahudFragment(title: String, description: String) {
        val fragment = tashahudFragment.newInstance(title, description)
        val transaction = supportFragmentManager.beginTransaction()
        // Set custom animations for the fragment transaction
//        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(fragment_container.id, fragment)
        transaction.commit()

        //make animation for the fragment_container
        fragment_container.visibility = View.VISIBLE
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fragment_container.startAnimation(animation)

    }

    fun hideTashahudFragment() {
        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.remove(supportFragmentManager.findFragmentById(fragment_container.id)!!)
        transaction.commit()
        //make animation for the fragment_container
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        fragment_container.startAnimation(animation)
        fragment_container.visibility = View.GONE
    }

    fun showCompleteFragment(string: String) {
        val fragment = CompleteFragment.newInstance(string)
        val transaction = supportFragmentManager.beginTransaction()
        // Set custom animations for the fragment transaction
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        transaction.replace(fragment_container.id, fragment)
        transaction.commit()
    }


    var proximitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] < proximitySensor.maximumRange) {
                        updateUI()
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }


    }


}