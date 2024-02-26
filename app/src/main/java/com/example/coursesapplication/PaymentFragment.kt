package com.example.coursesapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.coursesapplication.data.abstractClass
import com.example.coursesapplication.databinding.FragmentPaymentBinding
import com.example.coursesapplication.model.CoursesItem
//

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val courseList: ArrayList<CoursesItem> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var courseAdapter: CoursesAdapter // Create your adapter
    private var totalPrice: Double = 0.0
    private val selectedCourses = ArrayList<CoursesItem>()
    private lateinit var db: abstractClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerItem
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        courseAdapter = CoursesAdapter(courseList)
        recyclerView.adapter = courseAdapter
        db = Room.databaseBuilder(
            requireContext(),
            abstractClass::class.java,
            "coursesPayment_database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        courseList.clear()

        val courseCardList = db.dao().getCourseCard()
        courseList.addAll(courseCardList)
//        courseAdapter = CoursesAdapter(courseList)
//        recyclerView.adapter = courseAdapter
//        courseAdapter.notifyDataSetChanged()
            val imagePayment: ImageView = binding.imageView
            val titlePayment: TextView = binding.flutterPayment
            val ratePayment: TextView = binding.ratePayment
            val lessonPayment: TextView = binding.lessonPayment
            val paymentCoursePayment: TextView = binding.paymentCoursePayment
            val paymentText: TextView = binding.pay


            val imageResourceId = arguments?.getInt("image")
            val courseName = arguments?.getString("courseName")
            val rate = arguments?.getString("rate")
            val lessonName = arguments?.getString("lessonName")
            val payment = arguments?.getString("payment")

            imageResourceId?.let {
                imagePayment.setImageResource(it)
            }

            titlePayment.text = courseName
            ratePayment.text = rate
            lessonPayment.text = lessonName
            paymentCoursePayment.text = payment
            paymentText.text = payment

        val payNowBtn:Button=binding.payNow
        payNowBtn.setOnClickListener {
            Toast.makeText(requireActivity(),"pay successfully",Toast.LENGTH_SHORT).show()

            val receiveFragment:Fragment=ReceiveFragment()
            val fragmentTransaction:FragmentTransaction=parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.framemenu,receiveFragment).commit()
        }
//


//        db.dao().updateCourse(id)



//        val updatedCourseList = db.dao().getCourseCard()
//        courseList.clear()
//        courseList.addAll(updatedCourseList)
//
//        courseAdapter.notifyDataSetChanged()
        Log.d("mero",courseCardList.toString());




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming there's a button triggering the addition of courses
//            val selectedCourse = arguments?.getParcelable<CoursesItem>("coursesItem")
//
//            selectedCourse?.let {
//                if (selectedCourses.size < 3) { // Allow a maximum of 3 courses
//                    // Check the category of the selected course and add courses accordingly
//                    when (selectedCourse.Category) {
//                        "Flutter" -> {
//                            val flutterCourses = selectedCourses.filter { it.Category == "Flutter" }
//                            if (flutterCourses.size < 2) {
//                                selectedCourses.add(selectedCourse)
//                                courseAdapter.addCourse(selectedCourse)
//                                updateTotalPrice()
//                            } else {
//                                courseAdapter.addCourse(selectedCourse)
//                            }
//                        }
//                        "Kotlin" -> {
//                            val otherCourses = selectedCourses.filter { it.Category == "Kotlin" }
//                            if (otherCourses.size < 2) {
//                                selectedCourses.add(selectedCourse)
//                                courseAdapter.addCourse(selectedCourse)
//                                updateTotalPrice()
//                            } else {
//                                // Handle the case where 2 courses of this category are already added
//                            }
//                        }
//                        "Database" -> {
//                            val otherCourses = selectedCourses.filter { it.Category == "Database" }
//                            if (otherCourses.size < 2) {
//                                selectedCourses.add(selectedCourse)
//                                courseAdapter.addCourse(selectedCourse)
//                                updateTotalPrice()
//                            } else {
//                                // Handle the case where 2 courses of this category are already added
//                            }
//                        }
//                        // Add other categories as needed
//                    }
//                } else {
//                    // Handle a case where more than 3 courses are not allowed
//                }
//            }


    }

    private fun updateTotalPrice() {
        totalPrice = selectedCourses.sumByDouble { it.PaymentOfCourse.toDouble() }
        binding.paymentCoursePayment.text = totalPrice.toString()
    }


}



