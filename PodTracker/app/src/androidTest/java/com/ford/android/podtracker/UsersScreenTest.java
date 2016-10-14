package com.ford.android.podtracker;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ford.android.podtracker.users.UserListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by mshine7 on 23/09/2016.
 */
@RunWith(AndroidJUnit4.class)
public class UsersScreenTest {

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RecyclerView with text" + itemText);
            }

            @Override
            protected boolean matchesSafely(View item) {
                return allOf(isDescendantOfA(isAssignableFrom(RecyclerView.class)), withText(itemText)).matches(item);
            }
        };
    }

    @Rule
    public ActivityTestRule<UserListActivity> mUsersListActivityTestRule = new ActivityTestRule<>(UserListActivity.class);

    @Test
    public void clickAddUserButton_opensAddUserUi() {
//        onView(withId(R.id.fab_add_user)).perform(click());
//
//        onView(withId(R.id.et_name)).check(matches(isDisplayed()));
//        onView(withId(R.id.btn_next)).check(matches(isDisplayed()));
    }

//    @Test
//    public void addUserToUsersList() {
//        String newUserName = "Michael";
//
//        onView(withId(R.id.fab_add_user)).perform(click());
//
//        onView(withId(R.id.et_name)).perform(typeText(newUserName), closeSoftKeyboard());
//
//        onView(withId(R.id.btn_next)).perform(click());
//
//        onView(withId(R.id.rv_user_list)).perform(scrollTo(hasDescendant(withText(newUserName))));
//
//        onView(withItemText(newUserName)).check(matches(isDisplayed()));
//    }
}
