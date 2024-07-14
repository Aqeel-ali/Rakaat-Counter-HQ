package com.aqeel.rakaatcounterhq.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.aqeel.rakaatcounterhq.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UniversalBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var layoutResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutResId = it.getInt("layoutResId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    companion object {
        fun newInstance(layoutResId: Int): UniversalBottomSheetDialogFragment {
            val fragment = UniversalBottomSheetDialogFragment()
            val args = Bundle()
            args.putInt("layoutResId", layoutResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (layoutResId) {
            R.layout.bottom_sheet_settings -> {

            }
            R.layout.bottom_sheet_prayers_list -> {

            }
            R.layout.bottom_sheet_manual_customize -> {

                var rakaat2 = view.findViewById<Button>(R.id.rakaat2)
                var rakaat50 = view.findViewById<Button>(R.id.rakaat_50)
                var rakaat100 = view.findViewById<Button>(R.id.rakaat_100)
                var numberPicker = view.findViewById<com.shawnlin.numberpicker.NumberPicker>(R.id.number_picker)
                var startButton = view.findViewById<Button>(R.id.start_prayer)

                rakaat2.setOnClickListener {
                    numberPicker.value = 2
                }
                rakaat50.setOnClickListener {
                    numberPicker.value = 50
                }
                rakaat100.setOnClickListener {
                    numberPicker.value = 100
                }
                startButton.setOnClickListener {
                    // Start the prayer
                    val intent= Intent(context,PrayerActivity::class.java)
                    intent.putExtra("prayer","صلاة مخصصة")
                    intent.putExtra("image","added_ic")
                    intent.putExtra("rakaars",numberPicker.value)
                    context?.startActivity(intent)
                }



            }

        }
    }
}