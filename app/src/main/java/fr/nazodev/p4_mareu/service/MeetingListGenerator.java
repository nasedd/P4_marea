package fr.nazodev.p4_mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

public class MeetingListGenerator {

    //**************** Email list ********************//

    public static List<String> EMAIL_LIST = Arrays.asList("jean-yve@gmail.com","toto@exemple.fr","sangokou@dbz.com");
    static List<String> generateEmailList(){
        return new ArrayList<>(EMAIL_LIST);
    }


    //**************** Meeting list ********************//

    public static List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(Arrays.asList("jean-yve@gmail.com","toto@exemple.fr","sangokou@dbz.com"),"Rooms 2","Noël !","24/12","9h30"),
            new Meeting(Arrays.asList("maxim@lamzon.com","alex@lamzon.com","amandin@lamzon.com"),"Rooms 1","CDC-02 product pharma","01/01","5h00"),
            new Meeting(Arrays.asList("paul@lamzon.com","vivian@lamzon.com","luc@lamzon.com"),"Rooms 10","hebdo meeting","04/03","9h30"),
            new Meeting(Arrays.asList("jean-yve@gmail.com","toto@exemple.fr","sangokou@dbz.com"),"Rooms 7","Noël !","24/12","23h59")
    );
    static List<Meeting> generateMeetingList() { return new ArrayList<>(MEETING_LIST); }


    //**************** filtered meeting list ********************//

    public static List<Meeting> FILTERED_LIST = new ArrayList<>();
    static List<Meeting> generateFilteredList() { return new ArrayList<>(FILTERED_LIST); }

}
