package fr.nazodev.p4_mareu.service;

import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

public interface EmailApiService {

    //******* Email ***********//
    public List<String> getEmailList();
    public void deleteEmail(String email);
    public void addEmail(String email);

}
