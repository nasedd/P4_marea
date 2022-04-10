package fr.nazodev.p4_mareu.user_interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.model.Room;
import fr.nazodev.p4_mareu.repository.Repository;

public class MainActivity extends AppCompatActivity {

    Repository repository = DI.getRepository();
    List<Meeting> meetingList = repository.getMeetingList();
    MeetingFragment meetingFragment = new MeetingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository.setFilteredList(repository.getMeetingList());


        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, meetingFragment).commit();

        FloatingActionButton addButton = findViewById(R.id.floating_action_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddNewMeetingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        repository.clearFilteredList();

        Room selectedRoom = Room.findRoomById(item.getItemId());

        if (selectedRoom != null) {
            filterRoom(getString(selectedRoom.getStringRoom()));

        } else if (item.getItemId() == R.id.date_filter) {
            selectDate();

        } else if (item.getItemId() == R.id.no_filter) {
            repository.setFilteredList(repository.getMeetingList());
            refreshMeetingFragment();
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterRoom(String room) {
        for (Meeting meeting : meetingList) {
            if (meeting.location.equals(room)) {
                repository.addFilteredList(meeting);
            }
        }
        refreshMeetingFragment();
    }

    private void selectDate() {

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1);
                if (monthOfYear < 10) {
                    selectedDate = dayOfMonth + "/0" + (monthOfYear + 1);
                }
                if (dayOfMonth < 10) {
                    selectedDate = "0" + dayOfMonth + "/0" + (monthOfYear + 1);
                }
                filterByDate(selectedDate);
            }
        };
        // build
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        // show
        datePickerDialog.show();
    }

    private void filterByDate(String selectedDate) {
        for (Meeting meeting : meetingList) {
            if (meeting.date.equals(selectedDate)) {
                repository.addFilteredList(meeting);
            }
        }
        refreshMeetingFragment();
    }

    private void refreshMeetingFragment() {
        getSupportFragmentManager().beginTransaction().detach(meetingFragment).commit();
        getSupportFragmentManager().beginTransaction().attach(meetingFragment).commit();
    }


}