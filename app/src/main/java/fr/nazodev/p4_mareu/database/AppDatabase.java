package fr.nazodev.p4_mareu.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fr.nazodev.p4_mareu.model.Meeting;

@Database(entities = {Meeting.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MeetingDao meetingDao();
}
