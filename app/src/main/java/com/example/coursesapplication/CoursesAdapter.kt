package com.example.coursesapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.coursesapplication.data.abstractClass
import com.example.coursesapplication.data.courseDeo
import com.example.coursesapplication.model.CoursesItem


class CoursesAdapter(private var courseList: ArrayList<CoursesItem>) :
    RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {
    private lateinit var db: abstractClass

//    private val selectedCourses = HashSet<CoursesItem>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageCourse)
        val courseName: TextView = itemView.findViewById(R.id.flutterCourse)
        val rate: TextView = itemView.findViewById(R.id.rateCourse)
        val lessonsName: TextView = itemView.findViewById(R.id.lessonCourse)
        val payment: TextView = itemView.findViewById(R.id.paymentCourse)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)

        )

    }

    override fun getItemCount(): Int {
        return courseList.size
    }


@SuppressLint("SuspiciousIndentation")
override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentCourse = courseList[position]

    holder.image.setImageResource(currentCourse.Image)
    holder.courseName.text = currentCourse.NameOfCourses
    holder.rate.text = currentCourse.Rate.toString()
    holder.lessonsName.text = currentCourse.CountOfLessons.toString()
    holder.payment.text = currentCourse.PaymentOfCourse.toString()

    holder.itemView.setOnClickListener {
        val paymentFragment = PaymentFragment()
//        selectedCourses.add(currentCourse)

        val bundle = Bundle()

//       bundle.putParcelable("coursesItem", currentCourse)
//     bundle.putStringArrayList("image", selectedCourses) // Add currentCourse directly to bundle
            bundle.putInt("image", currentCourse.Image)
            bundle.putString("courseName", currentCourse.NameOfCourses)
            bundle.putString("rate", currentCourse.Rate.toString())
            bundle.putString("lessonName", currentCourse.CountOfLessons.toString())
            bundle.putString("payment", currentCourse.PaymentOfCourse.toString())


        val db = Room.databaseBuilder(
            holder.itemView.context, // Use appropriate Context
            abstractClass::class.java, // Replace with your database class
            "courses_database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()




        db.dao().updateCourse(courseList[position].reserved_id)
        paymentFragment.arguments = bundle


        val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.framemenu, paymentFragment)
            .addToBackStack(null)
            .commit()

    }


}

    fun setFiltereredList(courseList: ArrayList<CoursesItem>)
    {
        this.courseList=courseList
        notifyDataSetChanged()
    }

//    fun addCourse(course: String) {
//
//        courseList.add(course)
//        notifyDataSetChanged()
//    }


}



