package com.example.mareu;

import com.example.mareu.Controller.MainActivity;
import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.User;
import com.example.mareu.Utils.CustomDateFilterDialogClass;
import com.example.mareu.Utils.CustomRoomFilterDialogBox;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;



public class MareuUnitTest {

    @Before
    public void setUp() throws Exception {
        MainActivity.setMeetingList(new ArrayList<Meeting>());
        MainActivity.setSortedMeetingList(null);
    }

    @Test
    public void adding_meeting_test() {



        assertEquals(MainActivity.getMeetingList().size(),0);

        Meeting meeting_to_add=new Meeting();
        MainActivity.addMeeting(meeting_to_add);

        assertEquals(MainActivity.getMeetingList().size(),1);
        assertTrue(MainActivity.getMeetingList().contains(meeting_to_add));

    }

    @Test
    public void removing_meeting_test() {

        Meeting meeting_to_remove=new Meeting();
        MainActivity.addMeeting(meeting_to_remove);
        assertEquals(MainActivity.getMeetingList().size(),1);
        assertTrue(MainActivity.getMeetingList().contains(meeting_to_remove));
        MainActivity.removeMeeting(meeting_to_remove);
        assertEquals(MainActivity.getMeetingList().size(),0);
        assertFalse(MainActivity.getMeetingList().contains(meeting_to_remove));
    }

    @Test
    public void sortByRoom_test() {

        Meeting meeting_1=new Meeting(Calendar.getInstance().getTime(),"Room A","Subject",User.generateUserList());
        Meeting meeting_2=new Meeting(Calendar.getInstance().getTime(),"Room B","Subject",User.generateUserList());
        Meeting meeting_3=new Meeting(Calendar.getInstance().getTime(),"Room C","Subject",User.generateUserList());

        MainActivity.addMeeting(meeting_1);
        MainActivity.addMeeting(meeting_2);
        MainActivity.addMeeting(meeting_3);

        ArrayList<String> selectedRoom=new ArrayList<>();
        selectedRoom.add("Room A");

        CustomRoomFilterDialogBox mockedRoomFilter= mock(CustomRoomFilterDialogBox.class,CALLS_REAL_METHODS);
        mockedRoomFilter.sortListByRooms(selectedRoom);

        assertEquals(MainActivity.getMeetingList().size(),1);
        assertTrue(MainActivity.getMeetingList().contains(meeting_1));

    }

    @Test
    public void sortByDate_test() throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String firstDate="20200101";
        String secondDate="20200110";
        String thirdDate="20200120";
        Date date1=sdf.parse(firstDate);
        Date date2=sdf.parse(secondDate);
        Date date3=sdf.parse(thirdDate);

        Calendar calendarFrom=Calendar.getInstance();
        calendarFrom.set(2020,0,05);
        Calendar calendarTo=Calendar.getInstance();
        calendarTo.set(2020,0,15);


        Meeting meeting_1=new Meeting(date1,"Room A","Subject",User.generateUserList());
        Meeting meeting_2=new Meeting(date2,"Room B","Subject",User.generateUserList());
        Meeting meeting_3=new Meeting(date3,"Room C","Subject",User.generateUserList());

        MainActivity.addMeeting(meeting_1);
        MainActivity.addMeeting(meeting_2);
        MainActivity.addMeeting(meeting_3);

        CustomDateFilterDialogClass customDateFilterDialogClass=mock(CustomDateFilterDialogClass.class,CALLS_REAL_METHODS);
        customDateFilterDialogClass.sortListByDates(calendarFrom,calendarTo);

        assertEquals(MainActivity.getMeetingList().size(),1);
        assertTrue(MainActivity.getMeetingList().contains(meeting_2));
    }
}