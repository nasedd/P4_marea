package fr.nazodev.p4_mareu;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static fr.nazodev.p4_mareu.user_interface.AddNewMeetingActivity.formatDate;
import static fr.nazodev.p4_mareu.utils.RecyclerViewItemCountAssertion.expectedItemCount;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.nazodev.p4_mareu.user_interface.AddNewMeetingActivity;
import fr.nazodev.p4_mareu.utils.RecyclerViewItemCountAssertion;


@RunWith(AndroidJUnit4.class)
public class AddMeetingActivityTest {

    private static final int INITIAL_NUMBER_OF_ADDRESS_EMAIL = 3;
    private static final int YEAR = 2022;
    private static final int MONTH_OF_YEAR = 3;
    private static final int DAY_OF_MONTH = 4;
    private static final int ITEMS_COUNT = 3;
    @Rule
    public ActivityTestRule<AddNewMeetingActivity> scenarioRule = new ActivityTestRule<>(AddNewMeetingActivity.class);

    @Test
    public void addEmailAddress_isWorking(){
        onView(withId(R.id.participants_recycler_view)).check(expectedItemCount(INITIAL_NUMBER_OF_ADDRESS_EMAIL));
        onView(withId(R.id.edittext_participants)).perform(typeText("no valid @ address email"));
        onView(withId(R.id.edittext_participants)).perform(pressImeActionButton());
        onView(withId(R.id.participants_recycler_view)).check(expectedItemCount(INITIAL_NUMBER_OF_ADDRESS_EMAIL));
        onView(withId(R.id.edittext_participants)).perform(replaceText("valid@address.com"));
        onView(withId(R.id.edittext_participants)).perform(pressImeActionButton());
        onView(withId(R.id.participants_recycler_view)).check(expectedItemCount(INITIAL_NUMBER_OF_ADDRESS_EMAIL+1));
    }

    @Test
    public void chooseRoom_isWorking(){
        onView(withId(R.id.autoCompleteTextView)).perform(click());
        onView(withText(R.string.room_1)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.autoCompleteTextView)).check(matches(withText(R.string.room_1)));
    }

    @Test
    public void subjectField_isWorking(){
        onView(withId(R.id.editTextSubject)).perform(typeText("subject of the day..."));
        onView(withId(R.id.editTextSubject)).check(matches(withText("subject of the day...")));
    }

    @Test
    public void selectDate_isWorking(){
        onView(withId(R.id.editTextDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(YEAR, MONTH_OF_YEAR, DAY_OF_MONTH));
        onView(withId(android.R.id.button1)).perform(click()); // button1 -> OK button of the dialog
        String expectedDate = formatDate(DAY_OF_MONTH,MONTH_OF_YEAR-1);
        onView(withId(R.id.editTextDate)).check(matches(withText(expectedDate)));
        //c'est la bonne façon de fair d'appeler une fonction du code de l'appli pour testé l'appli ?
        //même si c'est que de la mise en forme de texte ?
    }

    @Test
    public void selectTime_isWorking(){
        onView(withId(R.id.editTextTime)).perform(click());
        //onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(13,20));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.editTextTime)).check(matches(withText("12:10")));
    }

    @Test
    public void CreateNewMeetingButton_shouldAddNewMeeting(){
        onView(withId(R.id.autoCompleteTextView)).perform(replaceText("Rooms 1"));
        onView(withId(R.id.editTextSubject)).perform(replaceText("subject of the day..."));
        onView(withId(R.id.editTextDate)).perform(replaceText("04/03"));
        onView(withId(R.id.editTextTime)).perform(replaceText("10:10"));
        onView(withId(R.id.button_validation_new_meeting)).perform(click());
        onView((withId(R.id.recyclerView))).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT+1));
    }


    public static ViewAction setTime(final int hour, final int minute) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                TimePicker tp = (TimePicker) view;
                tp.setCurrentHour(hour);
                tp.setCurrentMinute(minute);
            }
            @Override
            public String getDescription() {
                return "Set the passed time into the TimePicker";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }
        };
    }



}
