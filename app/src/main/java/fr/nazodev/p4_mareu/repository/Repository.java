package fr.nazodev.p4_mareu.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.nazodev.p4_mareu.database.AppDatabase;
import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.service.EmailApiService;
import fr.nazodev.p4_mareu.service.MeetingApiService;
import fr.nazodev.p4_mareu.service.FakeEmailApiService;

public class Repository {

    private final MeetingApiService apiService;
    private final EmailApiService apiService2;
    private final AppDatabase appDatabase;

    public Repository(MeetingApiService apiService, EmailApiService apiService2, AppDatabase appDatabase) {
        this.apiService = apiService;
        this.apiService2 = apiService2;
        this.appDatabase = appDatabase;
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
    public LiveData<List<Meeting>> getMeetingList(){
        return appDatabase.meetingDao().getMeetingList();
    }

    public void deleteMeeting(Meeting meeting){
        Thread thread1 = new Thread(){
            public void run(){  appDatabase.meetingDao().deleteMeeting(meeting); }
        };
        thread1.start();

    }
    public void addMeeting(Meeting meeting){
        Thread thread2 = new Thread(){
            public void run(){  appDatabase.meetingDao().addMeeting(meeting); }
        };
        thread2.start();
    }

    //********** Filtered list *************//
    public List<Meeting> getFilteredList(){

        return apiService.getFilteredList();
    }
    public void addFilteredList(Meeting meeting){
        apiService.addFilteredList(meeting);
    }
    public void clearFilteredList(){
        apiService.clearFilteredList();
    }
    public void setFilteredList(List<Meeting> list) {
        apiService.setFilteredList(list);
    }
    public void deleteFilteredList(Meeting meeting){
        apiService.deleteFilteredList(meeting);
    }
}
