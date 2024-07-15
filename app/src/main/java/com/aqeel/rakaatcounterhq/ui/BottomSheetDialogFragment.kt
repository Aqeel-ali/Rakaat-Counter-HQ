package com.aqeel.rakaatcounterhq.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.aqeel.rakaatcounterhq.Data.getInstructionsList
import com.aqeel.rakaatcounterhq.Data.getNawafilList
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.adapters.InstructionsAdabter
import com.aqeel.rakaatcounterhq.adapters.nawafilAdabter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trinnguyen.SegmentView

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

                var sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
                var incrementByClick = sharedPref.getBoolean("IncrementByClick", false)

                val segmentViewClick = view.findViewById<SegmentView>(R.id.segment_Click_settings)
                segmentViewClick.setText(0,"تعطيل")
                segmentViewClick.setText(1,"تفعيل")
                segmentViewClick.setSelectedIndex(if (incrementByClick) 1 else 0)

                val segmentViewSounds = view.findViewById<SegmentView>(R.id.segment_sounds_settings)
                segmentViewSounds.setText(0,"تعطيل")
                segmentViewSounds.setText(1,"تفعيل")
                segmentViewSounds.setSelectedIndex(1)

                segmentViewClick.setOnSegmentItemSelectedListener(object : SegmentView.OnSegmentItemSelectedListener {
                    override fun onSegmentItemSelected(position: Int) {

                        val editor = sharedPref.edit()
                        editor.putBoolean("IncrementByClick", position == 1)
                        editor.apply()
                        //change the value of incrementByClick in prayer activity
                        activity?.let {
                            if (it is PrayerActivity) {
                                it.updateIncrementByClick( position == 1)
                            }
                        }



                    }

                    override fun onSegmentItemReselected(index: Int) {
                        TODO("Not yet implemented")
                    }
                }
                )






            }
            R.layout.bottom_sheet_daily_nawafil -> {
            var recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.daily_nawafil_recycler)
             var nawafilList= getNawafilList()
            val adabter =nawafilAdabter(requireContext(),nawafilList)
            recyclerView.adapter = adabter
            recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context,2)


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
            R.layout.bottom_sheet_instructuins -> {
                var recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.instructions_recycler)
               val Instructionslist=  getInstructionsList()
                val adapter = InstructionsAdabter(requireContext(),Instructionslist)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

            }

        }
    }
}