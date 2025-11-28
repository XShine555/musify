package com.musify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.register_button)

        registerButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}