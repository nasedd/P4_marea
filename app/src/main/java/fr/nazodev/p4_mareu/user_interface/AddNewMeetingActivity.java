package fr.nazodev.p4_mareu.user_interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import com.google.android.material.datepicker.MaterialDatePicker;
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

        //************* RecyclerView adapter ***********************//
        RecyclerView recyclerView = findViewById(R.id.participants_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(AddNewMeetingActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new ParticipantRecyclerViewAdapter(emailList));

        //************* Modify ENTER_KEY behavior *****************//
        //************ add email or show error ********************//
        EditText emailInput = findViewById(R.id.edittext_participants);
        emailInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(isEmailValidate(emailInput.getEditableText())){
                        repository.addEmail(emailInput.getEditableText().toString());
                        recyclerView.setAdapter(new ParticipantRecyclerViewAdapter(emailList));
                        emailInput.setText("");
                    }else {
                        emailInput.setError("email not valid !");
                    }
                    handled = true;
                }
                return handled;
            }
        });


        //******************* Array and Adapter for the ROOM dropdown menu **********************//
        AutoCompleteTextView dropdownLocation = findViewById(R.id.autoCompleteTextView);
        String[] dropdownList = getResources().getStringArray(R.array.room_list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.dropdown_room_item,dropdownList );
        dropdownLocation.setAdapter(arrayAdapter);
        //hide keybord
        dropdownLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });


        //****************** set Date *******************************//
        EditText dateEditText = findViewById(R.id.editTextDate);
        //setDate(dateEditText);
        selectDate(dateEditText);

        //****************** set Time *******************************//
        EditText timeEditText = findViewById(R.id.editTextTime);
        setTime(timeEditText);

        //********************** Validation button -> create the new meeting *********************//
        EditText subject = findViewById(R.id.editTextSubject);
        Button validationButton = findViewById(R.id.button_validation_new_meeting);
        validationButton.setOnClickListener(v -> {

            if(emailList.isEmpty()){
                emailInput.setError("at least one participants !");
                emailInput.requestFocus();

            }else if(dropdownLocation.getEditableText().toString().equals("Choose the meeting room")){
                dropdownLocation.setError("chose a meeting room !");
                dropdownLocation.requestFocus();

            }else if(subject.getEditableText().toString().equals("")){
                subject.setError("subject required !");
                subject.requestFocus();

            }else if(dateEditText.getEditableText().toString().equals("")){
                dateEditText.requestFocus();

            }else if(timeEditText.getEditableText().toString().equals("")){
                timeEditText.requestFocus();

            }else{
                repository.addMeeting(new Meeting(
                        emailList,
                        dropdownLocation.getEditableText().toString(),
                        subject.getEditableText().toString(),
                        dateEditText.getEditableText().toString(),
                        timeEditText.getEditableText().toString()
                ));
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
                //AddNewMeetingActivity.this.finish(); //go back to meeting list
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
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            return true;
        }else{
            return false;
        }
    }

    private void setTime(EditText editText) {

        MaterialTimePicker materialTimePicker =  new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .build();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    materialTimePicker.show(getSupportFragmentManager(),"tag");
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTimePicker.show(getSupportFragmentManager(),"tag");
            }
        });
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

                String date = dayOfMonth + "/" + (monthOfYear+1);
                if(monthOfYear<10){
                    date = dayOfMonth + "/0" + (monthOfYear+1);
                }
                if(dayOfMonth <10){
                    date = "0" + dayOfMonth + "/0" + (monthOfYear+1);
                }
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
            if(hasFocus){
                datePickerDialog.show();
            }
        });

        //********** show Date Picker if editText get clicked ****************//
        editText.setOnClickListener(
                v -> datePickerDialog.show()
        );

    }

    private void setDate(EditText editText){

        //********** create the Date Picker *************//
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select dates for the meeting")
                .build();

        //********** show Date Picker if editText get the focus ****************//
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                materialDatePicker.show(getSupportFragmentManager(),"tag");
            }
        });

        //********** show Date Picker if editText get clicked ****************//
        editText.setOnClickListener(
                v -> materialDatePicker.show(getSupportFragmentManager(),"tag")
        );

        //**************** set editText with selected date *****************//
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> editText.setText(materialDatePicker.getHeaderText())
        );
    }

}