package com.ford.android.podtracker;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.ford.android.podtracker.users.UserListActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;

/**
 * Created by mshine7 on 23/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<UserListActivity> mActivityTestRule = new ActivityTestRule<>(UserListActivity.class);

    @Test
    public void clickOnStatisticsIcon_ShowStatisticsScreen() {

//        String userName = "Michael";
//
//        onView(withId(R.id.rv_user_list)).perform(RecyclerViewActions.actionOnItemAtPosition(4, new ClickOnImageView()));
//
//        //String[] expectedStatsLabels = { valueOf(R.string.pod_count), valueOf(R.string.current_month), valueOf(R.string.last_month) };
//
//        String[] expectedLabels = {getTargetContext().getString(R.string.pod_count), getTargetContext().getString(R.string.current_month), getTargetContext().getString(R.string.last_month)};
//
//        onView(withId(R.id.label_pod_count)).check(matches(withText(expectedLabels[0])));
//        onView(withId(R.id.label_current_month)).check(matches(withText(expectedLabels[1])));
//        onView(withId(R.id.label_last_month)).check(matches(withText(expectedLabels[2])));
    }


    public class ClickOnImageView implements ViewAction {

        @Override
        public Matcher<View> getConstraints() {
            return click().getConstraints();
        }

        @Override
        public String getDescription() {
            return " click on custom image view";
        }

        @Override
        public void perform(UiController uiController, View view) {
            click().perform(uiController, view.findViewById(R.id.iv_stats_temp));
        }
    }

}
