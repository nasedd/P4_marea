package fr.nazodev.p4_mareu.service;

import java.util.List;

public class FakeEmailApiService {

    private List<String> emailList = MeetingListGenerator.generateEmailList();


    //******* Email ***********//
    public List<String> getEmailList(){ return emailList; }

    public void deleteEmail(String email){
        emailList.remove(email);
    }

    public void addEmail(String email){
        emailList.add(email);
    }
}
