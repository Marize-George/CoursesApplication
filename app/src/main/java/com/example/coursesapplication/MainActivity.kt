package com.example.coursesapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginEmail: TextView =findViewById(R.id.loginEmail)
        val password: EditText =findViewById(R.id.passMain)
        val loginButton: Button =findViewById(R.id.login)
        val signUp: TextView =findViewById(R.id.signUp)
        val googleBtn:ImageView=findViewById(R.id.google)

//
//        val intent=Intent(this@MainActivity,HomePageActivity::class.java)
//        startActivity(intent)


//        val data: MutableMap<String, Any> = HashMap()
//        Firebase.firestore.collection("Users").add(data)


        signUp.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
//

        loginButton.setOnClickListener {
          //  val email=loginEmail.text.toString()
          //  val password=password.text.toString()

           // auth.signInWithEmailAndPassword(email,password)
             //   .addOnCompleteListener (this){
                   // if(it.isSuccessful){
                        val intent=Intent(this@MainActivity,HomePageActivity::class.java)
                        startActivity(intent)
                 //   }
//                    else{
//                        Toast.makeText(this@MainActivity,"email and password are invalid",Toast.LENGTH_LONG).show()
//                    }
               // }

        }

//    }
//}
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)

        findViewById<ImageView>(R.id.google).setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent : Intent = Intent(this , HomePageActivity::class.java)
//                intent.putExtra("email" , account.email)
//                intent.putExtra("name" , account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()

            }
        }
    }




}