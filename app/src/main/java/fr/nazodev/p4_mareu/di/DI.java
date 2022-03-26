package fr.nazodev.p4_mareu.di;


import fr.nazodev.p4_mareu.service.ParticipantApiService;
import fr.nazodev.p4_mareu.service.ParticipantsListGenerator;

/**
 * Dependency injector to get instance of services
 */

public class DI {

    static ParticipantApiService service = new ParticipantApiService();

    public static ParticipantApiService generateService(){
        return service;
    }
}