package com.devhp.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.devhp.newsapp.R

class InfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(requireActivity(), "onCreateView InfoFragment", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_info, container, false)
    }



}