package fr.nazodev.p4_mareu.repository;

import java.util.List;

import fr.nazodev.p4_mareu.service.MeetingApiService;
import fr.nazodev.p4_mareu.service.FakeEmailApiService;

public class Repository {

    private final MeetingApiService apiService;
    private final FakeEmailApiService apiService2;

    public Repository(MeetingApiService apiService, FakeEmailApiService apiService2) {
        this.apiService = apiService;
        this.apiService2 = apiService2;
    }

    //******* Email ***********//
    public List<String> getEmailList(){
        return apiService2.getEmailList();
    }
    public void deleteEmail(String email){
        apiService2.deleteEmail(email);
    }
    public void addEmail(String email){
        apiService2.addEmail(email);
    }

    //******** Meeting ********//
    public List<fr.nazodev.p4_mareu.model.Meeting> getMeetingList(){
        return apiService.getMeetingList();
    }
    public void deleteMeeting(fr.nazodev.p4_mareu.model.Meeting meeting){
        apiService.deleteMeeting(meeting);
    }
    public void addMeeting(fr.nazodev.p4_mareu.model.Meeting meeting){
        apiService.addMeeting(meeting);
    }

    //********** Filtered list *************//
    public List<fr.nazodev.p4_mareu.model.Meeting> getFilteredList(){
        return apiService.getFilteredList();
    }
    public void addFilteredList(fr.nazodev.p4_mareu.model.Meeting meeting){
        apiService.addFilteredList(meeting);
    }
    public void clearFilteredList(){
        apiService.clearFilteredList();
    }
    public void setFilteredList(List<fr.nazodev.p4_mareu.model.Meeting> list) {
        apiService.setFilteredList(list);
    }
    public void deleteFilteredList(fr.nazodev.p4_mareu.model.Meeting meeting){
        apiService.deleteFilteredList(meeting);
    }
}
