package fr.nazodev.p4_mareu.service;

import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

public interface MeetingApiService {

    //******** Meeting ********//
    List<Meeting> getMeetingList();
    void deleteMeeting(Meeting meeting);
    void addMeeting(Meeting meeting);

    //********** Filtered list *************//
    List<Meeting> getFilteredList();
    void addFilteredList(Meeting meeting);
    void clearFilteredList();
    void setFilteredList(List<Meeting> list);
    void deleteFilteredList(Meeting meeting);

}
