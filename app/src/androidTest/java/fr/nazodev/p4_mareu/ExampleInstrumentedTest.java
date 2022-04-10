package fr.nazodev.p4_mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;


import androidx.test.core.app.ActivityScenario;
//import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import fr.nazodev.p4_mareu.user_interface.MainActivity;
import fr.nazodev.p4_mareu.utils.DeleteMeetingViewAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
//@LargeTest //this annotation is the recommended way to annotate tests written with the AndroidX Test Library.
public class ExampleInstrumentedTest {

    //@Rule
    //public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("fr.nazodev.p4_mareu", appContext.getPackageName());
    }

    @Test
    public void addButton_shouldLaunch_AddMeetingActivity() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.floating_action_button)).perform(click());
        //intended(hasComponent(AddNewMeetingActivity.class.getName()));
        onView(withId(R.id.linearLayout)).check(matches(isDisplayed()));
    }
/*
    @Test
    public void deleteButton_should_deleteMeeting() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,new DeleteMeetingViewAction()));

    }

 */




}