package com.example.flashlight

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun testSwitch() {
        onView(withId(R.id.flashSwitch)).perform(ViewActions.click())
        onView(withId(R.id.flashSwitch)).check(matches(isChecked()))
        onView(withId(R.id.flashSwitch)).perform(ViewActions.click())
        onView(withId(R.id.flashSwitch)).check(matches(isNotChecked()))
    }

    @Test
    fun flashLight() {
        onView(withId(R.id.main)).perform(ViewActions.swipeDown())
        onView(withId(R.id.flashSwitch)).check(matches(isNotChecked()))
    }
}