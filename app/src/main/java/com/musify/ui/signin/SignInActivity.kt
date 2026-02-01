package com.musify.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.musify.R
import com.musify.databinding.ActivitySignInBinding
import com.musify.ui.MainActivity
import com.musify.ui.landing.LandingActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
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

        binding.signInButton.setOnClickListener {
            val username = binding.usernameInput.text?.toString().orEmpty()
            val password = binding.passwordInput.text?.toString().orEmpty()

            var anyError = false

            if (username.isEmpty()) {
                binding.usernameInputLayout.error = getString(R.string.error_username_empty)
                anyError = true
            }

            if (password.isEmpty()) {
                binding.passwordInputLayout.error = getString(R.string.error_password_empty)
                anyError = true
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