package com.example.android.testapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.testapp.ui.map.MapScreen
import com.example.android.testapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginScreen : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        loginCard.setOnClickListener {
                registrationUser()
        }
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    private fun registrationUser() {
        if (setEmail.text.isEmpty()) {
            Toast.makeText(this@LoginScreen, "Please set name", Toast.LENGTH_SHORT).show()
            return
        }
        if (setPassword.text.length < 6) {
            Toast.makeText(this@LoginScreen, "Please set password", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth!!.createUserWithEmailAndPassword(
            setEmail.text.toString(),
            setPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this@LoginScreen, "Try again later", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        val intent1 = Intent(this@LoginScreen, MapScreen::class.java)
        startActivity(intent1)
        Toast.makeText(this@LoginScreen, "Login", Toast.LENGTH_SHORT).show()
        finish()
    }
}