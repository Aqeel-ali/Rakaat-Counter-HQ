package com.aqeel.rakaatcounterhq.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aqeel.rakaatcounterhq.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"


class tashahudFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var title: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tashahud, container, false)
    }

    companion object {

        fun newInstance(title: String, description: String) = tashahudFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(ARG_TITLE)
        val description = arguments?.getString(ARG_DESCRIPTION)
        view.findViewById<TextView>(R.id.title_tashahud)?.text = title
        view.findViewById<TextView>(R.id.description_tashahud)?.text = description
    }
}