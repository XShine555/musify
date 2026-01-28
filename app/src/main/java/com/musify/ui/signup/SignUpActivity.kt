package com.musify.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.musify.databinding.ActivitySignUpBinding
import com.musify.ui.MainActivity
import com.musify.ui.landing.LandingActivity
import com.musify.ui.signin.SignInActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val username = binding.usernameInput.text?.toString().orEmpty()
            val password = binding.passwordInput.text?.toString().orEmpty()
            val confirmPassword = binding.confirmPasswordInput.text?.toString().orEmpty()

            binding.usernameInputLayout.error = null
            binding.passwordInputLayout.error = null
            binding.confirmPasswordInputLayout.error = null

            var anyError = false
            var isPasswordFieldEmpty = false

            if (username.isBlank()) {
                binding.usernameInputLayout.error = "El nombre de usuario no puede estar vacío."
                anyError = true
            }

            if (password.isBlank()) {
                binding.passwordInputLayout.error = "La contraseña no puede estar vacía."
                anyError = true
                isPasswordFieldEmpty = true
            }

            if (isPasswordFieldEmpty && confirmPassword.isEmpty()) {
                binding.confirmPasswordInputLayout.error = "La contraseña no puede estar vacía."
                anyError = true
            } else if (password != confirmPassword) {
                binding.confirmPasswordInputLayout.error = "Las contraseñas no coinciden."
                return@setOnClickListener
            }

            if (anyError) return@setOnClickListener

            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.goBackButton.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
        }
    }
}