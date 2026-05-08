package com.musify.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.musify.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class SignUpViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setup() {
        viewModel = SignUpViewModel()
    }

    @Test
    fun testUsernameEmpty() {
        viewModel.signUp("", "test@test.com", "1234", "1234")
        assertEquals(R.string.error_username_empty, viewModel.usernameError.value)
    }

    @Test
    fun testEmailInvalid() {
        viewModel.signUp("user", "invalid", "1234", "1234")
        assertEquals(R.string.error_email_invalid, viewModel.emailError.value)
    }

    @Test
    fun testPasswordsDoNotMatch() {
        viewModel.signUp("user", "test@test.com", "1234", "5678")
        assertEquals(R.string.error_passwords_not_match, viewModel.confirmPasswordError.value)
    }

    @Test
    fun testSignUpSuccess() {
        viewModel.signUp("user", "test@test.com", "1234", "1234")
        assertTrue(viewModel.signUpResult.value == true)
    }
}