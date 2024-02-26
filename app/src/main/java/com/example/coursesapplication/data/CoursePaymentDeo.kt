package com.example.coursesapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coursesapplication.model.CoursesItem
import com.example.coursesapplication.model.CoursesPaymentItem

@Dao

interface CoursePaymentDeo {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourses (coursesPaymentItem: CoursesPaymentItem)

    @Query("SELECT * FROM payment_course_entity WHERE reserved_id = :res_id")
    fun getCoursePayment(res_id: Int): List<CoursesPaymentItem>




}