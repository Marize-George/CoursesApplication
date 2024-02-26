package com.example.coursesapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.coursesapplication.databinding.FragmentAccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
         binding.logOut.setOnClickListener {
            var dialog= Dialog(requireContext())
            MaterialAlertDialogBuilder(requireContext())

            dialog.setContentView(R.layout.dialog)
            val open:Button=dialog.findViewById(R.id.open)
            val cancel:Button=dialog.findViewById(R.id.cancel)
            open.setOnClickListener{
              val intent=Intent(requireActivity(),MainActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            cancel.setOnClickListener{
                dialog.dismiss()
            }

            dialog.show()

         }

        return binding.root

    }

}