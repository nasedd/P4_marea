package fr.nazodev.p4_mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

public class MeetingListGenerator {

    public static List<String> EMAIL_LIST = Arrays.asList("jean-yve@gmail.com","toto@exemple.fr","sangokou@dbz.com");

    static List<String> generateEmailList(){
        return new ArrayList<>(EMAIL_LIST);
    }

    public static List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(Arrays.asList("jean-yve@gmail.com","toto@exemple.fr","sangokou@dbz.com"),"Room 2","New project debrief","24 Décembre","9h30"),
            new Meeting(Arrays.asList("maxim@lamzon.com","alex@lamzon.com","amandin@lamzon.com"),"Room 7","CDC-02 product pharma","1 Janvier","5h00"),
            new Meeting(Arrays.asList("paul@lamzon.com","vivian@lamzon.com","luc@lamzon.com"),"Room 10","hebdo meeting","14 juillet","9h30")
    );

    static List<Meeting> generateMeetingList() { return new ArrayList<>(MEETING_LIST); }
}
