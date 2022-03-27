package fr.nazodev.p4_mareu.service;

import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

public class MeetingApiService {

    private List<String> emailList = MeetingListGenerator.generateEmailList();
    private List<Meeting> meetingList = MeetingListGenerator.generateMeetingList();

    public List<String> getEmailList(){ return emailList; }

    public void deleteEmail(String email){
        emailList.remove(email);
    }

    public void addEmail(String email){
        emailList.add(email);
    }

    public List<Meeting> getMeetingList(){ return meetingList; }

    public void deleteMeeting(Meeting meeting){
        meetingList.remove(meeting);
    }

    public void addMeeting(Meeting meeting){
        meetingList.add(meeting);
    }
    




}
