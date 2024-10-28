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

class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_register = findViewById<Button>(R.id.button_register)
        val text_email = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextEmail)
        val text_password = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextPassword)
        auth = Firebase.auth

        btn_register.setOnClickListener {
            val email = text_email.text.toString()
            val password = text_password.text.toString()
            registerAccount(email, password)
        }

    }

    private fun registerAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext,
                        "Account created.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    redirectToHome()
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Account creation failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun redirectToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}