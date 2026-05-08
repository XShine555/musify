package com.musify.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.R

class SignUpViewModel : ViewModel() {
    private val _usernameError = MutableLiveData<Int?>()
    val usernameError: LiveData<Int?> = _usernameError

    private val _emailError = MutableLiveData<Int?>()
    val emailError: LiveData<Int?> = _emailError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<Int?>()
    val confirmPasswordError: LiveData<Int?> = _confirmPasswordError

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> = _signUpResult

    fun signUp(username: String, email: String, password: String, confirmPassword: String) {
        if (!validateInput(username, email, password, confirmPassword)) return
        _signUpResult.value = true
    }

    private fun validateInput(
        username: String, email: String, password: String, confirmPassword: String
    ): Boolean {
        var hasError = false

        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null

        if (username.isBlank()) {
            _usernameError.value = R.string.error_username_empty
            hasError = true
        }

        if (email.isBlank()) {
            _emailError.value = R.string.error_email_empty
            hasError = true
        } else if (!isValidEmail(email)) {
            _emailError.value = R.string.error_email_invalid
            hasError = true
        }

        if (password.isBlank()) {
            _passwordError.value = R.string.error_password_empty
            hasError = true
        }

        if (confirmPassword.isBlank()) {
            _confirmPasswordError.value = R.string.error_password_empty
            hasError = true
        } else if (password.isNotBlank() && confirmPassword != password) {
            _confirmPasswordError.value = R.string.error_passwords_not_match
            hasError = true
        }

        return !hasError
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}