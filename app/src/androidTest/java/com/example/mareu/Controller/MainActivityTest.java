package com.example.mareu.Controller;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.User;
import com.example.mareu.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Meeting meeting_1;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

        MainActivity.setMeetingList(new ArrayList<Meeting>());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String firstDate="20200101";
        String secondDate="20200110";
        String thirdDate="20200120";
        Date date1=sdf.parse(firstDate);
        Date date2=sdf.parse(secondDate);
        Date date3=sdf.parse(thirdDate);

        Calendar calendarFrom=Calendar.getInstance();
        calendarFrom.set(2020,0,05);
        Calendar calendarTo=Calendar.getInstance();
        calendarTo.set(2020,0,15);


        meeting_1=new Meeting(date1,"Room B","Subject", User.generateUserList());
        Meeting meeting_2=new Meeting(date2,"Room B","Subject",User.generateUserList());
        Meeting meeting_3=new Meeting(date3,"Room C","Subject",User.generateUserList());

        MainActivity.addMeeting(meeting_1);
        MainActivity.addMeeting(meeting_2);
        MainActivity.addMeeting(meeting_3);

        //display created meeting fragment
        CreatedMeetingFragment createdMeetingFragment=new CreatedMeetingFragment();
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_main,createdMeetingFragment)
                .commit();
    }

    /**
     * Meeting information are displayed
     */

    @Test
    public void displayedInformationCheck() {


        // check that recycler view is displayed
        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(isDisplayed()));

        //check that meeting1 description is displayed on screen
        ViewInteraction textView = onView(
                allOf(withId(R.id.fragment_meeting_TextView_Top), withText(meeting_1.toString()),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_meeting_Recycler_View),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(meeting_1.toString())));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.fragment_meeting_TextView_Bottom), withText(meeting_1.getParticipants().toString()),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_meeting_Recycler_View),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText(meeting_1.getParticipants().toString())));
    }

    /**
     * Deleting meeting test
     */

    @Test
    public void suppressMeetingTest() {

        CreatedMeetingFragment createdMeetingFragment=new CreatedMeetingFragment();
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_main,createdMeetingFragment)
                .commit();

        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(hasChildCount(MainActivity.getMeetingList().size())));

        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(hasChildCount(3)));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.delete_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_meeting_Recycler_View),
                                        0),
                                3),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(hasChildCount(MainActivity.getMeetingList().size())));

        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(hasChildCount(2)));
    }

    /**
     * Add meeting test
     */

    @Test
    public void addMeetingTest() {


        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.new_meeting_subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_subject),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("gdf"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.new_meeting_date),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_date),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());


        ViewInteraction appCompatMultiAutoCompleteTextView = onView(
                allOf(withId(R.id.autocomplete_participant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ExpandableListView_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatMultiAutoCompleteTextView.perform(click());



        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.new_meeting_button), withText("Create new meeting"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton3.perform(click());


        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.new_meeting_button), withText("Create new meeting"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton4.perform(click());

        //display created meeting fragment
        CreatedMeetingFragment createdMeetingFragment=new CreatedMeetingFragment();
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_main,createdMeetingFragment)
                .commit();

        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(hasChildCount(MainActivity.getMeetingList().size())));

        onView(withId(R.id.fragment_meeting_Recycler_View))
                .check(matches(hasChildCount(4)));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
