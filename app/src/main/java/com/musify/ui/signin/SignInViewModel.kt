package com.musify.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.R

class SignInViewModel: ViewModel() {
    private val _usernameError = MutableLiveData<Int?>()
    val usernameError: LiveData<Int?> = _usernameError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun signIn(username: String, password: String) {
        var hasError = false

        _usernameError.value = null
        _passwordError.value = null

        if (username.isBlank()) {
            _usernameError.value = R.string.error_username_empty
            hasError = true
        }

        if (password.isBlank()) {
            _passwordError.value = R.string.error_password_empty
            hasError = true
        }

        if (!hasError) {
            _loginSuccess.value = true
        }
    }
}