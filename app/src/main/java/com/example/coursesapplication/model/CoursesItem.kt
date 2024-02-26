package com.example.coursesapplication.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize


@Entity(tableName = "course_entity")
data class CoursesItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "image")
    val Image: Int,
    @ColumnInfo(name = "nameOfCourses")
    val NameOfCourses: String,
    @ColumnInfo(name = "category")
    val Category: String,
    @ColumnInfo(name = "rate")
    val Rate: Int,
    @ColumnInfo(name = "countOfLessons")
    val CountOfLessons: Int,
    @ColumnInfo(name = "paymentOfCourse")
    val PaymentOfCourse: Int,
    @ColumnInfo(name = "reserved_id")
    val reserved_id: Int,
    )
