package com.musify.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.R
import com.musify.api.Api
import com.musify.model.AuthRequest
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
    private val _usernameError = MutableLiveData<Int?>()
    val usernameError: LiveData<Int?> = _usernameError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<Int?>()
    val confirmPasswordError: LiveData<Int?> = _confirmPasswordError

    private val _accessToken = MutableLiveData<String?>()
    val accessToken: LiveData<String?> = _accessToken

    fun signUp(username: String, password: String, confirmPassword: String) {
        if (!validateInput(username, password, confirmPassword)) return

        viewModelScope.launch {
            performSignUp(username, password)
        }
    }

    private fun validateInput(username: String, password: String, confirmPassword: String): Boolean {
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

        return !hasError
    }

    private suspend fun performSignUp(username: String, password: String) {
        try {
            val response = Api.getAuthService().signUp(
                AuthRequest(username, password)
            )

            if (!response.isSuccessful) {
                Log.e("SignInViewModel", "Sign-in failed: ${response.code()} ${response.message()}")
                _usernameError.value = R.string.error_username_already_exists
                return
            }
            val accessToken = response.body()?.accessToken
            if (accessToken.isNullOrEmpty()) {
                Log.e("SignInViewModel", "Sign-in failed: Access token is empty")
                _usernameError.value = R.string.error_internal_server_error
                return
            }

            _accessToken.value = accessToken
        } catch (e: Exception) {
            Log.e("SignInViewModel", "Error during sign-in", e)
            return
        }
    }
}