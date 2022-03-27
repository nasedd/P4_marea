package fr.nazodev.p4_mareu.di;


import fr.nazodev.p4_mareu.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */

public class DI {

    static MeetingApiService service = new MeetingApiService();

    public static MeetingApiService getApiService(){
        return service;
    }
}