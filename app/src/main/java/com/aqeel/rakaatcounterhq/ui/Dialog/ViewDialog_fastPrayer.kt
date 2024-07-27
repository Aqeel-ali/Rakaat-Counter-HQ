package com.aqeel.rakaatcounterhq.ui.Dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.ui.UniversalBottomSheetDialogFragment

class ViewDialog {
    fun showResetPasswordDialog(activity: Activity?,supportFragment: FragmentManager?) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.viewdialog_fastprayer)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogBtn_remove = dialog.findViewById<Button>(R.id.close_dialogButton)
        dialogBtn_remove.setOnClickListener {
            dialog.dismiss()
            activity!!.finish()
        }
        var instructionsButton =dialog.findViewById<Button>(R.id.show_instructions)

        instructionsButton.setOnClickListener {

            //show bottom sheet
            UniversalBottomSheetDialogFragment.newInstance(R.layout.bottom_sheet_instructuins)
                .show(supportFragment!!, "instructions")

        }

        dialog.show()
    }
}