package fr.nazodev.p4_mareu.user_interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.database.AppDatabase;
import fr.nazodev.p4_mareu.database.MeetingDao;
import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.model.Rooms;
import fr.nazodev.p4_mareu.repository.Repository;

public class MainActivity extends AppCompatActivity {

    Repository repository;// = DI.getRepository();
    List<Meeting> meetingList;// = repository.getMeetingList();
    MeetingFragment meetingFragment;
    AppDatabase appDatabase;
    MeetingViewModel meetingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate Called");

        meetingFragment = new MeetingFragment();
        appDatabase = DI.instantiateAppDatabase(this); // need instantiate database before using repository bc repository use database
        meetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        repository = DI.getRepository();


        meetingViewModel.initFilteredList();

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

        Rooms selectedRoom = Rooms.findRoomById(item.getItemId());

        if (selectedRoom != null) {
            filterRoom(getString(selectedRoom.getStringRoom()));

        } else if (item.getItemId() == R.id.date_filter) {
            selectDate();

        } else if (item.getItemId() == R.id.no_filter) {
            meetingViewModel.initFilteredList();
        }

        return super.onOptionsItemSelected(item);
    }
    public void filterRoom(String room) {
        for (Meeting meeting : meetingList) {
            if (meeting.location.equals(room)) {
                meetingViewModel.filteredList.observe(this, filteredList -> filteredList.add(meeting));
                repository.addFilteredList(meeting);
            }
        }
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
        //meetingFragment.initList();

    }

    //replaced by MeetingFragment.initList()
    private void refreshMeetingFragment() {
        getSupportFragmentManager().beginTransaction().detach(meetingFragment).commit();
        getSupportFragmentManager().beginTransaction().attach(meetingFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart Called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop Called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
        Log.d("MainActivity", "onDestroy Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart Called");
    }
}