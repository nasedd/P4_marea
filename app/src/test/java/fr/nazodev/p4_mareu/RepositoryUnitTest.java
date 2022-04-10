package fr.nazodev.p4_mareu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static fr.nazodev.p4_mareu.service.MeetingListGenerator.EMAIL_LIST;
import static fr.nazodev.p4_mareu.service.MeetingListGenerator.MEETING_LIST;

import java.util.List;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.repository.Repository;
import fr.nazodev.p4_mareu.service.MeetingListGenerator;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RepositoryUnitTest {

    private Repository repository;

    @Before
    public void setup() { repository = DI.getNewInstanceOfRepository(); }


    @Test
    public void getEmailListWithSuccess() {
        List<String> emails = repository.getEmailList();
        List<String> expectedEmails = EMAIL_LIST; //need import
        assertEquals(emails, expectedEmails);
    }

    @Test
    public void deleteEmailWithSuccess() {
        List<String> emails = repository.getEmailList();
        String emailToDelete = repository.getEmailList().get(0);
        repository.deleteEmail(emailToDelete);
        assertFalse(emails.contains(emailToDelete));
    }

    @Test
    public void addEmailWithSuccess() {
        List<String> emails = repository.getEmailList();
        String newAddressEmail = "KB9@gmail.com";
        repository.addEmail(newAddressEmail);
        assertTrue(emails.contains(newAddressEmail));
    }

    @Test
    public void getMeetingListWithSuccess() {
        List<Meeting> meetingList = repository.getMeetingList();
        List<Meeting> expectedMeetingList = MeetingListGenerator.MEETING_LIST; //2nd way to access variable of Generator, no need import
        assertEquals(meetingList, expectedMeetingList);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        List<Meeting> meetingList = repository.getMeetingList();
        Meeting meetingToDelete = repository.getMeetingList().get(0);
        repository.deleteMeeting(meetingToDelete);
        assertFalse(meetingList.contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        List<Meeting> meetingList = repository.getMeetingList();
        Meeting newMeeting = new Meeting(EMAIL_LIST,"ROOM 7", "The Subject", "24/12", "16:45");
        repository.addMeeting(newMeeting);
        assertTrue(meetingList.contains(newMeeting));
    }

    @Test
    public void getFilteredListWithSuccess() {
        List<Meeting> filteredList = repository.getFilteredList();
        List<Meeting> expectedFilteredList = MeetingListGenerator.FILTERED_LIST;
        assertEquals(filteredList, expectedFilteredList);
    }

    @Test
    public void addFilteredListWithSuccess() {
        List<Meeting> filteredList = repository.getFilteredList();
        Meeting newMeeting = new Meeting(EMAIL_LIST,"ROOM 7", "The Subject", "24/12", "16:45");
        repository.addFilteredList(newMeeting);
        assertTrue(filteredList.contains(newMeeting));
    }

    @Test
    public void deleteFilteredListWithSuccess() {
        List<Meeting> filteredList = repository.getFilteredList();
        Meeting newMeeting = new Meeting(EMAIL_LIST,"ROOM 7", "The Subject", "24/12", "16:45");
        repository.addFilteredList(newMeeting);
        assertTrue(filteredList.contains(newMeeting));
        repository.deleteFilteredList(newMeeting);
        assertFalse(filteredList.contains(newMeeting));
    }
    @Test
    public void clearFilteredListWithSuccess() {
         List<Meeting> filteredList = repository.getFilteredList();
         Meeting newMeeting = new Meeting(EMAIL_LIST,"ROOM 7", "The Subject", "24/12", "16:45");
         repository.addFilteredList(newMeeting);
         assertTrue(filteredList.contains(newMeeting));
         repository.clearFilteredList();
         assertTrue(filteredList.isEmpty());
     }

    @Test
    public void setFilteredListWithSuccess() {
         repository.setFilteredList(MEETING_LIST);
         assertEquals(repository.getFilteredList(), MEETING_LIST);
    }


}