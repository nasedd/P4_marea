package fr.nazodev.p4_mareu.user_interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import fr.nazodev.p4_mareu.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

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


    MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                                                            .setTitleText("Select dates")
                                                            .build();
    MaterialTimePicker materialTimePicker =  new MaterialTimePicker.Builder()
                                                                        .setTimeFormat(TimeFormat.CLOCK_24H)
                                                                        .setHour(12)
                                                                        .setMinute(10)
                                                                        .build();
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.date_filter){
            //buttonSelectDate();
            materialDatePicker.show(getSupportFragmentManager(),"tag");
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override public void onPositiveButtonClick(Object selection) {

                    Toast.makeText(MainActivity.this, materialDatePicker.getHeaderText() + "-" ,Toast.LENGTH_SHORT).show();

                }
            });




        }

        if(item.getItemId() == R.id.room_1){
            materialTimePicker.show(getSupportFragmentManager(),"tag");
            materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, materialTimePicker.getHour() + " : " + materialTimePicker.getMinute() ,Toast.LENGTH_SHORT).show();
                }

            });


        }
        return super.onOptionsItemSelected(item);
    }







    private void buttonSelectDate() {

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                //editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                Toast.makeText(MainActivity.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year,Toast.LENGTH_SHORT).show();
            }
        };


        // Show
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, 2022,2,4);
        datePickerDialog.show();

    }
}

//Toast.makeText(MainActivity.this, "dayOfMonth + "-" + (monthOfYear + 1) + "-" + year",Toast.LENGTH_SHORT).show();