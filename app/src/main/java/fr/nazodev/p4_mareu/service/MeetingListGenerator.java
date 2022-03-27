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
            new Meeting(EMAIL_LIST,"Room 2","New project debrief","13 Avril 2022","9h30"),
            new Meeting(EMAIL_LIST,"Room 7","CDC-02 product pharma","13 Avril 2022","9h30"),
            new Meeting(EMAIL_LIST,"Room 10","hebdo meeting","13 Avril 2022","9h30")
    );

    static List<Meeting> generateMeetingList() { return new ArrayList<>(MEETING_LIST); }
}
