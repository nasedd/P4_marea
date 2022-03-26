package fr.nazodev.p4_mareu.service;

import java.util.List;

public class ParticipantApiService {

    private List<String> myList = ParticipantsListGenerator.generateList();

    public void deleteItem(String item){
        myList.remove(item);
    }

    public List<String> getMyList() {
        return myList;
    }




}
