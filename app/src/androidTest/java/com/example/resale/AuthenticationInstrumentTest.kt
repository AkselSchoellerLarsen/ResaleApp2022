package com.example.resale

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.lang.Exception
import java.util.*

@RunWith(AndroidJUnit4::class)
class AuthenticationInstrumentTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.resale", appContext.packageName)

        val email: String = getRandomEmail()
        val password: String = getRandomPassword()

        onView(withId(R.id.action_logout)).perform(click())

        waitForLogout()

        onView(withId(R.id.action_login)).perform(click())
        onView(withId(R.id.editText_email)).perform(typeText(email))
        onView(withId(R.id.editText_password)).perform(typeText(password))
        onView(withId(R.id.button_register)).perform(click())

        waitForLogin()

        onView(withId(R.id.action_logout)).check(matches(isDisplayed())).perform(click())

        waitForLogout()

        onView(withId(R.id.action_login)).perform(click())
        onView(withId(R.id.editText_email)).perform(typeText(email))
        onView(withId(R.id.editText_password)).perform(typeText(password))
        onView(withId(R.id.button_login)).perform(click())

        waitForLogin()

        onView(withId(R.id.action_logout)).check(matches(isDisplayed()))
    }

    private fun getRandomEmail(): String {
        var re: String = getRandomAlphaNumerical(6)
        re += "@"
        re += getRandomAlphaNumerical(4)
        re += "."
        re += getRandomAlphaNumerical(2)

        return re
    }
    private fun getRandomPassword(): String {
        return getRandomAlphaNumerical(18)
    }
    private fun getRandomAlphaNumerical(amount: Int): String {
        var re = ""
        val r = Random()
        for (i in 1..amount) {
            re += alphaNumericalOf(r.nextInt(17))
        }
        return re
    }
    private fun alphaNumericalOf(i: Int): String {
        when (i) {
            0 -> return "0"
            1 -> return "1"
            2 -> return "2"
            3 -> return "3"
            4 -> return "4"
            5 -> return "5"
            6 -> return "6"
            7 -> return "7"
            8 -> return "8"
            9 -> return "9"
            10 -> return "e"
            11 -> return "i"
            12 -> return "o"
            13 -> return "c"
            14 -> return "t"
            15 -> return "s"
            16 -> return "r"
        }
        return ""
    }

    private fun waitForLogin() {
        while(!doesViewExist(R.id.action_logout)) {
            Thread.sleep(1)
        }
    }
    private fun waitForLogout() {
        while(!doesViewExist(R.id.action_login)) {
            Thread.sleep(1)
        }
    }
    private fun doesViewExist(id: Int) : Boolean {
        try {
            onView(withId(id)).check(matches(isDisplayed()))
            return true
        } catch (e: Exception) {}
        return false
    }
}