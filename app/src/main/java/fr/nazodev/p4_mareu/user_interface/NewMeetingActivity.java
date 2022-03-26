package fr.nazodev.p4_mareu.user_interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.List;
import java.util.regex.Pattern;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.R;

public class NewMeetingActivity extends AppCompatActivity {

    private List<String> emailList = DI.generateService().getMyList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        RecyclerView recyclerView = findViewById(R.id.participants_recycler_view);
        recyclerView.setAdapter(new ParticipantRecyclerViewAdapter(emailList));

        EditText emailInput = findViewById(R.id.edittext_participants);
        emailInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(isEmailValidate(emailInput.getEditableText())){
                        emailList.add(emailInput.getEditableText().toString());
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

        AutoCompleteTextView dropdownLocation = findViewById(R.id.autoCompleteTextView);
        String[] dropdownList = getResources().getStringArray(R.array.room_list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.dropdown_room_item,dropdownList );
        dropdownLocation.setAdapter(arrayAdapter);


        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select dates")
                .build();
        MaterialTimePicker materialTimePicker =  new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .build();





    }

    private boolean isEmailValidate(Editable email) {
        String emailInput = email.toString();
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            return true;
        }else{
            return false;
        }
    }


}