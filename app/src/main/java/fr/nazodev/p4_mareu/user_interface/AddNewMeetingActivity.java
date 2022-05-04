package fr.nazodev.p4_mareu.user_interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.List;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.repository.Repository;

public class AddNewMeetingActivity extends AppCompatActivity {

    private Repository repository = DI.getRepository();
    private List<String> emailList = repository.getEmailList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        //************* RecyclerView for participants list ***********************//
        RecyclerView recyclerView = findViewById(R.id.participants_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(AddNewMeetingActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new ParticipantRecyclerViewAdapter(emailList));


        //************ add email ********************//
        EditText emailInput = findViewById(R.id.edittext_participants);
        emailInput.setOnEditorActionListener(new TextView.OnEditorActionListener() { // Listener on keyboard
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {  // if user press ENTER_KEY

                    if (isEmailValidate(emailInput.getEditableText())) {
                        repository.addEmail(emailInput.getEditableText().toString());
                        recyclerView.setAdapter(new ParticipantRecyclerViewAdapter(emailList));
                        emailInput.setText("");
                    } else {
                        emailInput.setError("email not valid !");
                    }
                    handled = true;
                }
                return handled;
            }
        });
        //hide keyboard when not in the field
        emailInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });


        //******************* ROOM dropdown menu **********************//
        AutoCompleteTextView dropdownLocation = findViewById(R.id.autoCompleteTextView);
        //usage in OnResume()


        //***************** subject input ************************//
        EditText subjectInput = findViewById(R.id.editTextSubject);
        //hide keyboard when not in the field
        subjectInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });


        //****************** set Date *******************************//
        EditText dateEditText = findViewById(R.id.editTextDate);
        dateEditText.setInputType(InputType.TYPE_NULL); //defining the input type via xml did not work. Why ?
        selectDate(dateEditText); //listener inside function

        //****************** set Time *******************************//
        EditText timeEditText = findViewById(R.id.editTextTime);

        timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setTime(timeEditText);
                }
            }
        });
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(timeEditText);
            }
        });

        //********************** Validation button -> create the new meeting *********************//
        Button validationButton = findViewById(R.id.button_validation_new_meeting);
        validationButton.setOnClickListener(v -> {

            if (emailList.isEmpty()) {
                emailInput.setError("at least one participants !");
                emailInput.requestFocus();

            } else if (dropdownLocation.getEditableText().toString().equals("Choose the meeting room")) {
                dropdownLocation.setError("chose a meeting room !");
                dropdownLocation.requestFocus();

            } else if (subjectInput.getEditableText().toString().equals("")) {
                subjectInput.setError("subject required !");
                subjectInput.requestFocus();

            } else if (dateEditText.getEditableText().toString().equals("")) {
                dateEditText.requestFocus();

            } else if (timeEditText.getEditableText().toString().equals("")) {
                timeEditText.requestFocus();

            } else {
                repository.addMeeting(new Meeting(
                        emailList,
                        dropdownLocation.getEditableText().toString(),
                        subjectInput.getEditableText().toString(),
                        dateEditText.getEditableText().toString(),
                        timeEditText.getEditableText().toString()
                ));
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        dropdownLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dropdownLocation.setError(null);
            }
        });

    }

    private boolean isEmailValidate(Editable email) {
        String emailInput = email.toString();
        return !emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
    }

    private void setTime(EditText editText) {

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .build();

        materialTimePicker.show(getSupportFragmentManager(), "tag");

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeSelected = materialTimePicker.getHour() + ":" + materialTimePicker.getMinute();
                editText.setText(timeSelected);
            }

        });
    }

    private void selectDate(EditText editText) {

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String date = formatDate(dayOfMonth,monthOfYear);
                editText.setText(date);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        //********** show Date Picker if editText get the focus ****************//
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.show();
            }
        });

        //********** show Date Picker if editText get clicked ****************//
        editText.setOnClickListener(v -> datePickerDialog.show());

    }

    public static String formatDate(int dayOfMonth, int monthOfYear) {
        String date = dayOfMonth + "/" + (monthOfYear + 1);
        if (monthOfYear < 10) {
            date = dayOfMonth + "/0" + (monthOfYear + 1);
        }
        if (dayOfMonth < 10) {
            date = "0" + dayOfMonth + "/0" + (monthOfYear + 1);
        }
        return date;
    }

    @Override
    public void onResume() {
        super.onResume();
        //******************* ROOM dropdown menu **********************//
        AutoCompleteTextView dropdownLocation = findViewById(R.id.autoCompleteTextView);
        String[] dropdownList = getResources().getStringArray(R.array.room_list); // get the array for the dropdown menu
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_room_item, dropdownList); //the dropdown need an adapter
        dropdownLocation.setAdapter(arrayAdapter);

    }


}