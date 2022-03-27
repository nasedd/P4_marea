package fr.nazodev.p4_mareu.model;


import java.util.List;

public class Meeting {

    List<String> participants;
    String location;
    String subject;
    String date;
    String time;

    public Meeting(List<String> participants, String location, String subject, String date, String time){
        this.participants = participants;
        this.location = location;
        this.subject = subject;
        this.date = date;
        this.time = time;
    }

}
