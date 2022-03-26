package fr.nazodev.p4_mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticipantsListGenerator {

    public static List<String> emailList= Arrays.asList("jean-yve@gmail.com","toto@exemple.fr","sangokou@dbz.com");

    static List<String> generateList(){
        return new ArrayList<>(emailList);
    }


}
