package fr.nazodev.p4_mareu.database;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.nazodev.p4_mareu.model.Meeting;

@Dao
public interface MeetingDao {

    // *** GET ***
    @Query("SELECT * FROM Meeting")
    LiveData<List<Meeting>> getMeetingList();

    // *** DELETE ***
    @Delete
    void deleteMeeting(Meeting meeting);

    // *** ADD ***
    @Insert
    void addMeeting(Meeting meeting); //room implement it ! room will insert (in database) all object/entity passed in the constructor

}
