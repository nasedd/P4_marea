package fr.nazodev.p4_mareu.di;


import android.content.Context;

import androidx.room.Room;

import fr.nazodev.p4_mareu.database.AppDatabase;
import fr.nazodev.p4_mareu.repository.Repository;
import fr.nazodev.p4_mareu.service.EmailApiService;
import fr.nazodev.p4_mareu.service.FakeEmailApiService;
import fr.nazodev.p4_mareu.service.FakeMeetingApiService;
import fr.nazodev.p4_mareu.service.MeetingApiService;

/**
 * Dependency injector to get instance of Repository & inject services
 */
//Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
    //-->.allowMainThreadQueries().build();

// The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
// This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
// It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
public class DI {

    private static volatile AppDatabase DATABASE; // = null if not instantiate
    public static AppDatabase instantiateAppDatabase(Context context){
        if(DATABASE == null){
            synchronized (AppDatabase.class){
                if(DATABASE == null){
                    DATABASE = Room.databaseBuilder(context,
                            AppDatabase.class, "database-name").build();
                }
            }
        }
        return DATABASE;
    }

    static MeetingApiService service = new FakeMeetingApiService();
    static EmailApiService service2 = new FakeEmailApiService();


    public static Repository getRepository(){
        return new Repository(service, service2, DATABASE);
    }

    /**
     * Get always a new instance on @{@link Repository}. Useful for tests, so we ensure the context is clean.
     */
    public static Repository getNewInstanceOfRepository(){
        return new Repository(new FakeMeetingApiService(), new FakeEmailApiService(), DATABASE);
    }
}



