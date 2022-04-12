package fr.nazodev.p4_mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;


import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static fr.nazodev.p4_mareu.utils.RecyclerViewItemCountAssertion.expectedItemCount;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.repository.Repository;
import fr.nazodev.p4_mareu.user_interface.AddNewMeetingActivity;
import fr.nazodev.p4_mareu.user_interface.MainActivity;
import fr.nazodev.p4_mareu.utils.DeleteMeetingViewAction;
import fr.nazodev.p4_mareu.utils.RecyclerViewItemCountAssertion;


@RunWith(AndroidJUnit4.class)
@LargeTest//this annotation is the recommended way to annotate tests written with the AndroidX Test Library.
public class MainActivityInstrumentedTest {

    //***************deleteMeeting et deleteMeeting2 conflict ********************

    private static final int NUMBER_OF_MEETING_WITH_ROOM1 = 1;
    private static final int NUMBER_OF_MEETING_WITH_DATE_0304 = 1;
    Repository repository = DI.getRepository();

    private MainActivity mActivity;

    @Rule
    //public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }


    @Test
    public void addButton_shouldLaunch_AddMeetingActivity() {
        //ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.floating_action_button)).perform(click());

        //methode 1 :

        Intents.intended(hasComponent(AddNewMeetingActivity.class.getName()));

        //methode 2 :
        //onView(withId(R.id.linearLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void deleteButton_should_deleteMeeting() {
        int numberOfMeeting = repository.getMeetingList().size();
        onView((withId(R.id.recyclerView))).check(expectedItemCount(numberOfMeeting));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,new DeleteMeetingViewAction()));
        onView((withId(R.id.recyclerView))).check(expectedItemCount(numberOfMeeting-1));
    }

    @Test
    public void filterByRoom(){
        onView(withId(R.id.appBar_menu)).perform(click());
        onView(withText(R.string.room_1)).perform(click());
        onView(withId(R.id.recyclerView)).check(expectedItemCount(NUMBER_OF_MEETING_WITH_ROOM1));
    }

    @Test
    public void noFilterTest(){
        int numberOfMeeting = repository.getMeetingList().size();
        onView(withId(R.id.appBar_menu)).perform(click());
        onView(withText(R.string.no_filter)).perform(click());
        onView(withId(R.id.recyclerView)).check(expectedItemCount(numberOfMeeting));
    }

    @Test
    public void dateFilterTest(){
        onView(withId(R.id.appBar_menu)).perform(click());
        onView(withText(R.string.date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 3, 4));
        onView(withId(android.R.id.button1)).perform(click()); // button1 -> OK button of the dialog
        onView(withId(R.id.recyclerView)).check(expectedItemCount(NUMBER_OF_MEETING_WITH_DATE_0304));
    }

}