package com.aqeel.rakaatcounterhq.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        }
    }
}