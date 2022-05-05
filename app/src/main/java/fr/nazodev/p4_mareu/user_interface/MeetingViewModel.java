package fr.nazodev.p4_mareu.user_interface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.repository.Repository;

public class MeetingViewModel extends ViewModel {

    private final Repository repository = DI.getRepository();

    LiveData<List<Meeting>> meetingList = repository.getMeetingList();
    LiveData<List<Meeting>> filteredList;


    public LiveData<List<Meeting>> getMeetingList(){ return repository.getMeetingList(); }
    public void initFilteredList() { filteredList = getMeetingList(); }



    public void deleteMeeting(Meeting meeting){ repository.deleteMeeting(meeting);}

    public void addMeeting(Meeting meeting){ repository.addMeeting(meeting);}
}
