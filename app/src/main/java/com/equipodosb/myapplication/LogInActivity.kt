package com.equipodosb.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
        val currentUser = auth.currentUser
        var btn_log_in = findViewById<Button>(R.id.button_log_in)
        var btn_register = findViewById<Button>(R.id.button_register)
        var btn_forgot_password = findViewById<Button>(R.id.button_forgot_password)

        if (currentUser != null) {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_log_in.setOnClickListener {
            var email = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextEmail).text.toString()
            var password = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextPassword).text.toString()
            logIn(email, password)
        }
        btn_register.setOnClickListener {
            redirectToRegister()
        }
        btn_forgot_password.setOnClickListener {
            redirectToForgotPassword()
        }

    }

    fun logIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Authentication successful. ${user?.email}", Toast.LENGTH_SHORT).show()
                    redirectToHome()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun redirectToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun redirectToRegister() {
        val intent = Intent(this, RegisterAccountActivity::class.java)
        startActivity(intent)

    }
    private fun redirectToForgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)

    }
}