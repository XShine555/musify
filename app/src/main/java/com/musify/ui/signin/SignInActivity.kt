package com.musify.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.musify.databinding.ActivitySignInBinding
import com.musify.ui.MainActivity
import com.musify.ui.landing.LandingActivity
import androidx.core.content.edit

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private val viewModel: SignInViewModel by viewModels()

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

        viewModel.usernameError.observe(this) { errorRes ->
            binding.usernameInputLayout.error =
                errorRes?.let { getString(it) }
        }

        viewModel.passwordError.observe(this) { errorRes ->
            binding.passwordInputLayout.error =
                errorRes?.let { getString(it) }
        }

        viewModel.accessToken.observe(this) { accessToken ->
            if (accessToken.isNullOrEmpty()) return@observe

            val sharedPreferences = getSharedPreferences("auth_preferences", MODE_PRIVATE)
            sharedPreferences.edit { putString("access_token", accessToken) }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.signInButton.setOnClickListener {
            viewModel.signIn(
                username = binding.usernameInput.text?.toString().orEmpty(),
                password = binding.passwordInput.text?.toString().orEmpty()
            )
        }

        binding.goBackButton.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
        }
    }
}