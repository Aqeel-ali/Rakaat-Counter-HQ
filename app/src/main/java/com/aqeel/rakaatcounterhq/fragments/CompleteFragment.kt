package com.aqeel.rakaatcounterhq.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqeel.rakaatcounterhq.R
import com.aqeel.rakaatcounterhq.adapters.CompleteAdabter

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PRAYER = "prayer"


class CompleteFragment : Fragment() {
private lateinit var recycler_view_complete : RecyclerView
private var prayer: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            prayer = it.getString(ARG_PRAYER)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complete, container, false)
    }


    companion object {


        @JvmStatic
        fun newInstance(prayer: String) =
            CompleteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PRAYER, prayer)
                }
            }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    // Initialize the RecyclerView
    recycler_view_complete = view.findViewById(R.id.recycler_complete)

    // Create a list of strings
    val elements = mutableListOf(
        "الانتقال إلى تسبيح الزهراء ع",
        "الانتقال إلى تعقيبات الصلاة"
    )

        if (prayer=="صلاة الظهر"){
            elements.add("الانتقال إلى صلاة العصر")
        }
        else if (prayer=="صلاة العصر"){
            elements.add("الانتقال إلى صلاة المغرب")
        }
        else if (prayer=="صلاة المغرب"){
            elements.add("الانتقال إلى صلاة العشاء")
        }
        else if (prayer=="صلاة الصباح"){
            elements.add("الانتقال إلى صلاة الظهر")
        }


    // Set LayoutManager
    recycler_view_complete.layoutManager = LinearLayoutManager(context)

    // Create and Set Adapter
    val adapter = CompleteAdabter(requireContext(), elements,prayer!!)
    recycler_view_complete.adapter = adapter

}
}