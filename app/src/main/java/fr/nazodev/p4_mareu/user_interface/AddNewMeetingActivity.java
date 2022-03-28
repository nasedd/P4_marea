package fr.nazodev.p4_mareu.user_interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.List;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.service.MeetingApiService;

public class AddNewMeetingActivity extends AppCompatActivity {

    private MeetingApiService apiService = DI.getApiService();
    private List<String> emailList = apiService.getEmailList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        //************* RecyclerView adapter ***********************//
        RecyclerView recyclerView = findViewById(R.id.participants_recycler_view);
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
                        apiService.addEmail(emailInput.getEditableText().toString());
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


        //****************** set Date *******************************//
        EditText dateEditText = findViewById(R.id.editTextDate);
        setDate(dateEditText);

        //****************** set Time *******************************//
        EditText timeEditText = findViewById(R.id.editTextTime);
        setTime(timeEditText);

        //********************** Validation button -> create the new meeting *********************//
        EditText subject = findViewById(R.id.editTextSubject);
        Button validationButton = findViewById(R.id.button_validation_new_meeting);
        validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailList.isEmpty()){
                    emailInput.setError("at least one participants !");
                    emailInput.requestFocus();
                }else if(dropdownLocation.getEditableText().toString().equals("Choose the meeting room")){
                    //Toast.makeText(AddNewMeetingActivity.this,"chose a room !",Toast.LENGTH_SHORT).show();
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
                    apiService.addMeeting(new Meeting(
                            emailList,
                            dropdownLocation.getEditableText().toString(),
                            subject.getEditableText().toString(),
                            dateEditText.getEditableText().toString(),
                            timeEditText.getEditableText().toString()
                    ));
                }

            }
        });

        dropdownLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dropdownLocation.setError(null);
            }
        });





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
                    materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String timeSelected = materialTimePicker.getHour() + "h" + materialTimePicker.getMinute();
                            editText.setText(timeSelected);
                        }

                    });
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTimePicker.show(getSupportFragmentManager(),"tag");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String timeSelected = materialTimePicker.getHour() + ":" + materialTimePicker.getMinute();
                        editText.setText(timeSelected);
                    }

                });
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

    private void setDate(EditText editText){
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select dates for the meeting")
                .build();
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //Toast.makeText(NewMeetingActivity.this,"heaaay",Toast.LENGTH_SHORT).show();
                    materialDatePicker.show(getSupportFragmentManager(),"tag");
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                        @Override public void onPositiveButtonClick(Object selection) {
                            editText.setText(materialDatePicker.getHeaderText());

                        }
                    });
                }

            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"tag");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override public void onPositiveButtonClick(Object selection) {
                        editText.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });
    }


}