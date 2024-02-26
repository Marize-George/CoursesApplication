package com.example.coursesapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.coursesapplication.databinding.FragmentAccountBinding
import com.example.coursesapplication.databinding.FragmentReceiveBinding


class ReceiveFragment : Fragment() {

    private var _binding: FragmentReceiveBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReceiveBinding.inflate(inflater, container, false)
        binding.issue.setOnClickListener {
            val chatFragment:Fragment=ChatFragment()
            val fragmentTransaction:FragmentTransaction=parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.framemenu,chatFragment).commit()
        }
        return binding.root

    }

}