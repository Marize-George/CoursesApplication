package com.example.coursesapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "payment_course_entity")

data class CoursesPaymentItem(

    @PrimaryKey(autoGenerate = true) val reserved_id:Int =0,
//    @ColumnInfo(name = "image") val Image: Int,
//    @ColumnInfo(name = "nameOfCourses") val NameOfCourses: String,
//    @ColumnInfo(name = "category") val Category: String,
//    @ColumnInfo(name = "rate") val Rate:Int,
//    @ColumnInfo(name = "countOfLessons") val CountOfLessons:Int,
//    @ColumnInfo(name = "paymentOfCourse") val PaymentOfCourse:Int,
)
