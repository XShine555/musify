package com.musify.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.R

class SignUpViewModel: ViewModel() {
    private val _usernameError = MutableLiveData<Int?>()
    val usernameError: LiveData<Int?> = _usernameError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<Int?>()
    val confirmPasswordError: LiveData<Int?> = _confirmPasswordError

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> = _signUpSuccess

    fun signUp(username: String, password: String, confirmPassword: String) {
        var hasError = false

        _usernameError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null

        if (username.isBlank()) {
            _usernameError.value = R.string.error_username_empty
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

        if (!hasError) {
            _signUpSuccess.value = true
        }
    }
}