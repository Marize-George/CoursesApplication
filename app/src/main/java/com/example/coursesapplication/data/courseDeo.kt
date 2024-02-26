package com.example.coursesapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coursesapplication.model.CoursesItem


@Dao
interface courseDeo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourses (coursesItem: CoursesItem)


    @Query("SELECT * FROM course_entity WHERE category = :category")
    fun getCourse(category: String): List<CoursesItem>


    @Query("DELETE FROM course_entity" )
    fun deleteCourseById()

    @Query("Update course_entity set reserved_id=1 WHERE id = :id")
    fun updateCourse(id: Int)

//    @Query("SELECT * FROM course_entity WHERE reserved_id=1")
//    fun getCourseCard(category: Int): List<CoursesItem>

    @Query("SELECT * FROM course_entity WHERE reserved_id=1")
    fun getCourseCard(): List<CoursesItem>


}



