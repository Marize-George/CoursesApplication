package com.example.coursesapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coursesapplication.model.CoursesItem

@Database(entities = [CoursesItem::class], version = 2)
abstract class abstractClass :
    RoomDatabase()  {

    abstract fun dao(): courseDeo


}




