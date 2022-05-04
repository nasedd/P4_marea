package fr.nazodev.p4_mareu.model;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Meeting {

    @PrimaryKey(autoGenerate = true)
    public int meetingId;
    public List<String> participants;
    public String location;
    public String subject;
    public String date;
    public String time;


    public Meeting(List<String> participants, String location, String subject, String date, String time){
        this.participants = participants;
        this.location = location;
        this.subject = subject;
        this.date = date;
        this.time = time;

    }
}
