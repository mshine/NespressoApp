package com.ford.android.podtracker;

import android.support.test.rule.ActivityTestRule;

import com.ford.android.podtracker.adduser.AddUserActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mshine7 on 23/09/2016.
 */
public class AddUserActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<AddUserActivity> addUserActivityActivityTestRule = new ActivityTestRule<>(AddUserActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.et_name)).check(matches(withHint(R.string.enter_your_name)));
        onView(withId(R.id.et_name)).perform(typeText("Matt")).check(matches(withText("Matt")));
    }

    @Test
    public void validateButton() {
        onView(withId(R.id.btn_next)).check(matches(withText(R.string.next)));
    }
}
