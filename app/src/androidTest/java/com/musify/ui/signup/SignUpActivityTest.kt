package com.musify.ui.signup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musify.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun testEmptyFieldsShowErrors() {
        onView(withId(R.id.sign_up_button)).perform(click())

        onView(withId(R.id.username_input_layout))
            .check(matches(hasDescendant(withText(R.string.error_username_empty))))
        onView(withId(R.id.email_input_layout))
            .check(matches(hasDescendant(withText(R.string.error_email_empty))))
        onView(withId(R.id.password_input_layout))
            .check(matches(hasDescendant(withText(R.string.error_password_empty))))
        onView(withId(R.id.confirm_password_input_layout))
            .check(matches(hasDescendant(withText(R.string.error_password_empty))))
    }

    @Test
    fun testInvalidEmailShowsError() {
        onView(withId(R.id.email_input)).perform(typeText("correo-no-valido"), closeSoftKeyboard())
        onView(withId(R.id.sign_up_button)).perform(click())

        onView(withId(R.id.email_input_layout))
            .check(matches(hasDescendant(withText(R.string.error_email_invalid))))
    }

    @Test
    fun testPasswordMismatchShowsError() {
        onView(withId(R.id.username_input)).perform(typeText("user"), closeSoftKeyboard())
        onView(withId(R.id.email_input)).perform(typeText("test@test.com"), closeSoftKeyboard())
        onView(withId(R.id.password_input)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.confirm_password_input)).perform(typeText("5678"), closeSoftKeyboard())

        onView(withId(R.id.sign_up_button)).perform(click())

        onView(withId(R.id.confirm_password_input_layout))
            .check(matches(hasDescendant(withText(R.string.error_passwords_not_match))))
    }

    @Test
    fun testSuccessfulSignUp() {
        onView(withId(R.id.username_input)).perform(typeText("user"), closeSoftKeyboard())
        onView(withId(R.id.email_input)).perform(typeText("test@test.com"), closeSoftKeyboard())
        onView(withId(R.id.password_input)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.confirm_password_input)).perform(typeText("1234"), closeSoftKeyboard())

        onView(withId(R.id.sign_up_button)).perform(click())
    }
}