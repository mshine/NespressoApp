package com.ford.android.podtracker;

import com.ford.android.podtracker.data.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class PodTrackerTests {

    @Test
    public void testMaxPodsInASingleDayIs20() {
        User user = new User();
        user.setPodCount(20);

        //TODO implement increment method
        user.incrementPodCount();

        assertThat(user.getPodCount(), is(20));
    }

    @Test
    public void testPodCountCannotGoBelow0() {
        User user = new User();
        user.setPodCount(0);

        //TODO implement decrement method
        user.decrementPodCount();

        assertThat(user.getPodCount(), is(0));
    }

    @Test
    public void testUserNameCanOnlyContainLetters() {
        User user = new User();
        user.setName("H3!!0");

        // DbHelper cannot get application context? mocking?

        Pattern r = compile("^[A-Za-z]+$");

        Matcher m = r.matcher(user.getName());

        //assertThat(user.getName(), );

        assertThat(true, is(true));
    }

    @Test
    public void testAdapterIntialisiedWithCorrectParameters() {

    }

    @Test
    public void testAdapterDisplaysCorrectNumberOfItemsFromDb() {

    }

    @Test
    public void testUserNameDoesNotAlreadyExist() {

    }

    @Test
    public void testGetNoOfPodsForLastMonth_CheckParameters() {

    }

    @Test
    public void testGetNoOfPodsForCurrentMonth_CheckParameters() {

    }

    @Test
    public void testGetNoOfPodsForLastMonth_CheckDateBoundaries() {

    }

    @Test
    public void testGetNoOfPodsForCurrentMonth_CheckDateBoundaries() {

    }

    @Test
    public void testCheckTransitionFromUserListActivityToAddUserActivity() {

    }

    @Test
    public void testCheckTransitionFromAddUserActivityToUserListActivity() {

    }

    @Test
    public void testCheckIntentExtraFromAddUserActivityToUserListActivity() {
        //mock user object
        User user = mock(User.class);
        //is make text called
    }

    @Test
    public void testCheckBackTransitionFromAddUserActivityToUserListActivity() {

    }

    @Test
    public void testCheckTransitionFromUserListActivityToStatsActivity() {

    }

    @Test
    public void testCheckTransitionFromUserListActivityToAddPodActivity() {

    }

    @Test
    public void testCheckIntentExtraFromUserListActivityToAddPodActivity() {

    }

    @Test
    public void testCheckIntentExtraFromAddPodActivityToUserListActivity() {

    }

    @Test
    public void testCheckBackTransitionFromAddPodActivityToUserListActivity() {

    }

}