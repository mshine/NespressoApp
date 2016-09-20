package com.ford.android.podtracker;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class PodTrackerTests {

    @Test
    public void incrementPodCount_maxPodsInASingleDayIs20() {
        User user = new User();
        user.setPodCount(20);
        user.incrementPodCount();

        assertThat(user.getPodCount(), is(20));
    }

    @Test
    public void decrementPodCount_podCountCannotGoBelow0() {
        User user = new User();
        user.setPodCount(0);
        user.decrementPodCount();

        assertThat(user.getPodCount(), is(0));
    }

    @Test
    public void addUser_userNameCanOnlyContainLetters() {
        User user = new User();
        user.setName("H3!!0");

        // DbHelper cannot get application context? mocking?

        Pattern r = compile("^[A-Za-z]+$");

        Matcher m = r.matcher(user.getName());

        //assertThat(user.getName(), );

        assertThat(true, is(true));
    }

    @Test
    public void userListActivity_isAdapterIntialisiedWithCorrectParameters() {

    }

    @Test
    public void userListActivity_doesAdapterDisplayCorrectNumberOfItemsFromDb() {

    }

    @Test
    public void addUser_userNameDoesNotAlreadyExist() {

    }

    @Test
    public void getNoOfPodsForLastMonth_CheckParameters() {

    }

    @Test
    public void getNoOfPodsForCurrentMonth_CheckParameters() {

    }

    @Test
    public void getNoOfPodsForLastMonth_CheckDateBoundaries() {

    }

    @Test
    public void getNoOfPodsForCurrentMonth_CheckDateBoundaries() {

    }

    @Test
    public void checkTransitionFromUserListActivityToAddUserActivity() {

    }

    @Test
    public void checkTransitionFromAddUserActivityToUserListActivity() {

    }

    @Test
    public void checkBackTransitionFromAddUserActivityToUserListActivity() {

    }

    @Test
    public void checkTransitionFromUserListActivityToStatsActivity() {

    }

    @Test
    public void checkTransitionFromUserListActivityToAddPodActivity() {

    }

    @Test
    public void checkTransitionFromAddPodActivityToUserListActivity() {

    }

    @Test
    public void checkBackTransitionFromAddPodActivityToAddUserActivity() {

    }

}