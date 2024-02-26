package com.example.coursesapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomePageActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val bottomnavigation: BottomNavigationView =findViewById(R.id.id_bottomnavigation)
        bottomnavigation.setOnItemSelectedListener{
            when(it.itemId){

                R.id.home->{
                    val coursesFragment: Fragment =CoursesFragment()
                    val fragmentransation: FragmentTransaction =supportFragmentManager.beginTransaction()
                    fragmentransation.replace(R.id.framemenu,coursesFragment).commit()
                    true

                }
//                R.id.courses->{
//                    val paymentFragment: Fragment =PaymentFragment()
//                    val fragmentransation: FragmentTransaction =supportFragmentManager.beginTransaction()
//                    fragmentransation.replace(R.id.framemenu,paymentFragment).commit()
//                    true
//
//                }
                R.id.chat->{
                    val chatFragment: Fragment =ChatFragment()
                    val fragmentransation: FragmentTransaction =supportFragmentManager.beginTransaction()
                    fragmentransation.replace(R.id.framemenu,chatFragment).commit()
                    true

                }
                R.id.account->{
                    val accountFragment: Fragment =AccountFragment()
                    val fragmentransation: FragmentTransaction =supportFragmentManager.beginTransaction()
                    fragmentransation.replace(R.id.framemenu,accountFragment).commit()
                    true

                }

                else ->{
                    true
                }
            }



        }


    }
}