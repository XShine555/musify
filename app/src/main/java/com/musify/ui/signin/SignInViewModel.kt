package com.musify.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.R
import com.musify.api.Api
import com.musify.model.AuthRequest
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _usernameError = MutableLiveData<Int?>()
    val usernameError: LiveData<Int?> = _usernameError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _accessToken = MutableLiveData<String?>()
    val accessToken: LiveData<String?> = _accessToken

    fun signIn(username: String, password: String) {
        if (!validateInput(username, password)) return

        viewModelScope.launch {
            performSignIn(username, password)
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
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

        return !hasError
    }

    private suspend fun performSignIn(username: String, password: String) {
        try {
            val response = Api.getAuthService().signIn(
                AuthRequest(username, password)
            )

            if (!response.isSuccessful) {
                Log.e("SignInViewModel", "Sign-in failed: ${response.code()} ${response.message()}")
                _passwordError.value = R.string.error_invalid_credentials_or_username_not_found
                return
            }
            val accessToken = response.body()?.accessToken
            if (accessToken.isNullOrEmpty()) {
                Log.e("SignInViewModel", "Sign-in failed: Access token is empty")
                _passwordError.value = R.string.error_internal_server_error
                return
            }

            _accessToken.value = accessToken
        } catch (e: Exception) {
            Log.e("SignInViewModel", "Error during sign-in", e)
            return
        }
    }
}