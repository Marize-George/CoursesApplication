package com.example.coursesapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coursesapplication.model.CoursesPaymentItem

@Database(entities = [CoursesPaymentItem::class], version = 1)

abstract class abstractCourse :
    RoomDatabase() {
    abstract fun dao(): CoursePaymentDeo

}



