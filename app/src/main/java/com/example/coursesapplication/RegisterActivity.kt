package com.example.coursesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        val name: EditText = findViewById(R.id.name)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.pass)
        val confirmPassword: EditText = findViewById(R.id.confirmPass)
//        val passsordREg = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        val registerButton: Button = findViewById(R.id.register)
        val signIn: TextView = findViewById(R.id.signIn)
        val checkBox = findViewById<CheckBox>(R.id.id_checkbox)







        registerButton.setOnClickListener {
            val userName = name.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            val confirmPassword = confirmPassword.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(this@RegisterActivity, "username is required", Toast.LENGTH_LONG)
                    .show()
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@RegisterActivity, "email is required", Toast.LENGTH_LONG).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@RegisterActivity, "password is required", Toast.LENGTH_LONG)
                    .show()
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(
                    this@RegisterActivity,
                    "confirmPassword is required",
                    Toast.LENGTH_LONG
                ).show()
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(
                    this@RegisterActivity,
                    "password doesn't match confirmPassword",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (checkBox.isChecked) {
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@RegisterActivity, "Not Checked" + "", Toast.LENGTH_LONG).show()

            }
            registerUser(userName, email, password)
        }
        signIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid
//                    val intent = Intent(this@RegisterActivity, HomePageActivity::class.java)
//                    startActivity(intent)

                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("profileImage", "")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {

                            val intent = Intent(this@RegisterActivity, HomePageActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

    }
}