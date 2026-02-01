package com.musify.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.musify.R
import com.musify.databinding.ActivitySignUpBinding
import com.musify.ui.MainActivity
import com.musify.ui.landing.LandingActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val originalPaddingStart = binding.root.paddingTop
        val originalPaddingTop = binding.root.paddingTop
        val originalPaddingEnd = binding.root.paddingEnd
        val originalPaddingBottom = binding.root.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                originalPaddingStart + systemBars.left,
                originalPaddingTop + systemBars.top,
                originalPaddingEnd + systemBars.right,
                originalPaddingBottom + systemBars.bottom
            )
            insets
        }

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
                binding.usernameInputLayout.error = getString(R.string.error_username_empty)
                anyError = true
            }

            if (password.isBlank()) {
                binding.passwordInputLayout.error = getString(R.string.error_password_empty)
                anyError = true
                isPasswordFieldEmpty = true
            }

            if (isPasswordFieldEmpty && confirmPassword.isEmpty()) {
                binding.confirmPasswordInputLayout.error = getString(R.string.error_password_empty)
                anyError = true
            } else if (password != confirmPassword) {
                binding.confirmPasswordInputLayout.error = getString(R.string.error_passwords_not_match)
                return@setOnClickListener
            }

            if (anyError) return@setOnClickListener

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.goBackButton.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
        }
    }
}