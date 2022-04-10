package fr.nazodev.p4_mareu.di;


import fr.nazodev.p4_mareu.repository.Repository;
import fr.nazodev.p4_mareu.service.FakeEmailApiService;
import fr.nazodev.p4_mareu.service.FakeMeetingApiService;

/**
 * Dependency injector to get instance of Repository & inject services
 */

public class DI {

    static FakeMeetingApiService service = new FakeMeetingApiService();
    static FakeEmailApiService service2 = new FakeEmailApiService();

    public static Repository getRepository(){
        return new Repository(service, service2);
    }

    /**
     * Get always a new instance on @{@link Repository}. Useful for tests, so we ensure the context is clean.
     */
    public static Repository getNewInstanceOfRepository(){
        return new Repository(new FakeMeetingApiService(), new FakeEmailApiService());
    }
}



