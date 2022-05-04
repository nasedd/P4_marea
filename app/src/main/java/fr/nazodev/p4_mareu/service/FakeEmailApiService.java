package fr.nazodev.p4_mareu.service;

import java.util.List;

public class FakeEmailApiService implements EmailApiService{

    private List<String> emailList = MeetingListGenerator.generateEmailList();


    //******* Email ***********//
    @Override
    public List<String> getEmailList(){ return emailList; }
    @Override
    public void deleteEmail(String email){
        emailList.remove(email);
    }
    @Override
    public void addEmail(String email){
        emailList.add(email);
    }
}
