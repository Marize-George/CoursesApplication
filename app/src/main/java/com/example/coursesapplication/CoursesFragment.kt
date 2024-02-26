package com.example.coursesapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.coursesapplication.data.abstractClass
import com.example.coursesapplication.data.courseDeo
import com.example.coursesapplication.model.CoursesItem
import java.util.Locale


class CoursesFragment : Fragment() {
    private lateinit var img: ImageView
    private val images = intArrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )
    private var currentIndex = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var  listadapter: CoursesAdapter
    private val courseList: ArrayList<CoursesItem> = ArrayList()
    private val allCoursesList: ArrayList<Courses> = ArrayList()
    private lateinit var db: abstractClass
    private lateinit var dao: courseDeo
    private var allCourses: List<CoursesItem> = emptyList() // Store all courses here

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_courses, container, false)



        db = Room.databaseBuilder(
            requireContext(),
            abstractClass::class.java,
            "courses_database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        img = view.findViewById<ImageView>(R.id.image)

        val handler = Handler()
        val imageRunnable = object : Runnable {
            override fun run() {
                changeImage()
                handler.postDelayed(this, 3000) // Change image every 3 seconds (3000 milliseconds)
            }
        }

        handler.postDelayed(imageRunnable, 3000) // Initial delay before first image change
        recyclerView = view.findViewById(R.id.recycler)
        searchView=view.findViewById(R.id.searchView)

        addAllCourses()
        allCourses = ArrayList(courseList) // Store a copy of all courses
        val allCoursesButton: Button = view.findViewById(R.id.AllCourses)
        val flutterButton: Button = view.findViewById(R.id.flutterItem)
        val kotlinButton: Button = view.findViewById(R.id.kotlinItem)
        val databaseButton: Button = view.findViewById(R.id.databaseItem)
        val originalBackground = allCoursesButton.background
        val highlightedBackground = ContextCompat.getDrawable(requireContext(), R.drawable.star)

        allCoursesButton.setOnClickListener {
//            val allCourses = db.dao().getCourse("") // Fetch all courses
//            showCourses(allCourses)
//            courseList.clear()
//            courseList.addAll(db.dao().getCourse("") )// Fetch Flutter courses
            courseList.clear()
            courseList.addAll(allCourses) // Populate courseList with all courses
            showCourses(courseList)
            val listadapter = CoursesAdapter(courseList)
            recyclerView.adapter = listadapter
            allCoursesButton.background = highlightedBackground
            flutterButton.background = originalBackground
            kotlinButton.background = highlightedBackground
            databaseButton.background = highlightedBackground


        }

        flutterButton.setOnClickListener {
            courseList.clear()
            courseList.addAll(db.dao().getCourse("Flutter") )// Fetch Flutter courses
            //   showCourses(flutterCourses)
            val listadapter = CoursesAdapter(courseList)
            recyclerView.adapter = listadapter
            flutterButton.background = highlightedBackground
            allCoursesButton.background = originalBackground
        }

        kotlinButton.setOnClickListener {
            courseList.clear()
            courseList.addAll(db.dao().getCourse("Kotlin") )// Fetch Flutter courses
            //   showCourses(flutterCourses)
            val listadapter = CoursesAdapter(courseList)
            recyclerView.adapter = listadapter
            kotlinButton.background = highlightedBackground
            allCoursesButton.background = originalBackground
        }


        databaseButton.setOnClickListener {
            courseList.clear()
            val databaseCourses = db.dao().getCourse("Database") // Fetch Database courses

            courseList.addAll(databaseCourses)
            showCourses(courseList)
            databaseButton.background = highlightedBackground
            allCoursesButton.background = originalBackground
        }

        // Set up RecyclerView with all courses initially
        showCourses(courseList)


        return view
    }






    private fun changeImage() {
        img.setImageResource(images[currentIndex])
        currentIndex = (currentIndex + 1) % images.size
    }
    // Function to show courses in the RecyclerView
    private fun showCourses(courses: List<CoursesItem>) {
        val listAdapter = CoursesAdapter(courseList)
        recyclerView.adapter = listAdapter

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false)
         listadapter = CoursesAdapter(courseList)
        recyclerView.adapter = listadapter

   searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
       override fun onQueryTextSubmit(query: String?): Boolean {
return false
       }

       override fun onQueryTextChange(newText: String?): Boolean {
           if (newText != null) {
               filterList(newText)
           }
           return true
       }
   })


    }
    private fun addAllCourses() {
        courseList.clear()

        // Add all courses to the allCoursesList

        val image1: CoursesItem = CoursesItem(1,R.drawable.image1,"Flutter Fundamentals", "Flutter", 5, 150, 500,1)
        courseList.add(image1)
        val image2: CoursesItem = CoursesItem(2,R.drawable.image2, "Flutter & Dart", "Flutter", 4, 200, 1000,2)
        courseList.add(image2)


        val image3: CoursesItem = CoursesItem(3,R.drawable.image3, "Flutter Architecture", "Flutter", 5, 100, 750,3)
        courseList.add(image3)

        val image4: CoursesItem = CoursesItem(4,R.drawable.image4, "Flutter Responsive UI", "Flutter", 4, 500, 1500,4)
        courseList.add(image4)

        val image5: CoursesItem = CoursesItem(5,R.drawable.image5, "Kotlin Fundamentals", "Kotlin", 5, 150, 500,5)
        courseList.add(image5)

        val image6: CoursesItem = CoursesItem(6,R.drawable.image6, "Kotlin", "Kotlin", 4, 200, 1000,6)
        courseList.add(image6)

        val image7: CoursesItem = CoursesItem(7,R.drawable.image7, "Kotlin Architecture", "Kotlin", 5, 100, 750,7)
        courseList.add(image7)

        val image8: CoursesItem = CoursesItem(8,R.drawable.image8, "Kotlin Responsive UI", "Kotlin", 4, 500, 1500,8)
        courseList.add(image8)

        val image9: CoursesItem = CoursesItem(9,R.drawable.image9, "Database Fundamentals", "Database", 5, 150, 500,9)
        courseList.add(image9)

        val image10: CoursesItem = CoursesItem(10,R.drawable.image10, "Sqlite", "Database", 4, 200, 1000,10)
        courseList.add(image10)

        val image11: CoursesItem = CoursesItem(11,R.drawable.image11, "Database Architecture", "Database", 5, 100, 750,11)
        courseList.add(image11)

        val image12: CoursesItem = CoursesItem(12,R.drawable.image12, "Database Responsive UI", "Database", 4, 500, 1500,12)
        courseList.add(image12)
//
        db.dao().insertCourses(image1)
        db.dao().insertCourses(image2)
        db.dao().insertCourses(image3)
        db.dao().insertCourses(image4)
        db.dao().insertCourses(image5)
        db.dao().insertCourses(image6)
        db.dao().insertCourses(image7)
        db.dao().insertCourses(image8)
        db.dao().insertCourses(image9)
        db.dao().insertCourses(image10)
        db.dao().insertCourses(image11)
        db.dao().insertCourses(image12)


        db.dao().updateCourse(id)
//         courseList.addAll(db.dao().getCourseCard())



    }


 private fun filterList(query: String){
     if(query!= null)
     {
         val filterList =ArrayList<CoursesItem>()
         for(i in courseList)
         {
             if(i.NameOfCourses.toLowerCase(Locale.ROOT).contains(query))
             {
                 filterList.add(i)
             }
             if(filterList.isEmpty())
             {
                 Toast.makeText(requireActivity(),"No Data Found",Toast.LENGTH_SHORT).show()
             }
             else{
                 listadapter.setFiltereredList(filterList)

             }
         }
     }
 }
}





























