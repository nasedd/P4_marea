package fr.nazodev.p4_mareu.service;

import java.util.ArrayList;
import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

public class FakeMeetingApiService implements MeetingApiService {

    private List<Meeting> meetingList = MeetingListGenerator.generateMeetingList();
    private List<Meeting> filteredList = MeetingListGenerator.generateFilteredList();


    //******** Meeting ********//
    @Override
    public List<Meeting> getMeetingList(){ return meetingList; }
    @Override
    public void deleteMeeting(Meeting meeting){
        meetingList.remove(meeting);
    }
    @Override
    public void addMeeting(Meeting meeting){
        meetingList.add(meeting);
    }

    //********** Filtered list *************//
    @Override
    public List<Meeting> getFilteredList(){
        return filteredList;
    }
    @Override
    public void addFilteredList(Meeting meeting){
        filteredList.add(meeting);
    }
    @Override
    public void clearFilteredList(){
        filteredList.clear();
    }
    @Override
    public void setFilteredList(List<Meeting> list) {
        filteredList = new ArrayList<>(list);
    }
    @Override
    public void deleteFilteredList(Meeting meeting){
        filteredList.remove(meeting);
    }





}
