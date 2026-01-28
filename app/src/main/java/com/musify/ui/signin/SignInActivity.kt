package com.musify.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.musify.databinding.ActivitySignInBinding
import com.musify.ui.MainActivity
import com.musify.ui.signup.SignUpActivity
import com.musify.ui.landing.LandingActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            val username = binding.usernameInput.text?.toString().orEmpty()
            val password = binding.passwordInput.text?.toString().orEmpty()

            var anyError = false

            if (username.isEmpty()) {
                binding.usernameInputLayout.error = "El nombre de usuario no puede estar vacío."
                anyError = true
            }

            if (password.isEmpty()) {
                binding.passwordInputLayout.error = "La contraseña no puede estar vacía."
                anyError = true
            }

            if (anyError) return@setOnClickListener

            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.goBackButton.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
        }
    }
}