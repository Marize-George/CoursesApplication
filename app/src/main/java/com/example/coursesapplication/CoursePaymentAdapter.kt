package com.example.coursesapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursesapplication.model.CoursesItem
import com.example.coursesapplication.model.CoursesPaymentItem

class CoursePaymentAdapter(private var paymentCourseList: ArrayList<CoursesItem>) :
    RecyclerView.Adapter<CoursePaymentAdapter.ViewHolder>(){
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imagePaymentCourse)
        val courseName: TextView = itemView.findViewById(R.id.flutterPaymentCourse)
        val rate: TextView = itemView.findViewById(R.id.ratePaymentCourse)
        val lessonsName: TextView = itemView.findViewById(R.id.lessonPaymentCourse)
        val payment: TextView = itemView.findViewById(R.id.paymentPaymentCourse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.payment_item, parent, false)
        )    }

    override fun getItemCount(): Int {
        return paymentCourseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCourse = paymentCourseList[position]

        holder.image.setImageResource(currentCourse.Image)
        holder.courseName.text = currentCourse.NameOfCourses
        holder.rate.text = currentCourse.Rate.toString()
        holder.lessonsName.text = currentCourse.CountOfLessons.toString()
        holder.payment.text = currentCourse.PaymentOfCourse.toString()    }
}




