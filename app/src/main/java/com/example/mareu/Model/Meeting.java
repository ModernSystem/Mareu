package com.example.mareu.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting implements Serializable {

    private Date mSchedule;
    private String mSubject;
    private List<User> mParticipants;
    private String mPlace;
    public static final List<User> mUserList = new ArrayList<>();

    /**
     * Constructors
     */

    public Meeting() {
    }

    public Meeting(Date schedule, String place, String subject, List<User> participants) {
        this.mSchedule = schedule;
        this.mParticipants = participants;
        this.mSubject = subject;
        this.mPlace = place;
    }


    /**
     * Getters and Setters
     */


    public Date getSchedule() {
        return mSchedule;
    }

    public void setSchedule(Date schedule) {
        mSchedule = schedule;
    }


    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public List<User> getParticipants() {
        return mParticipants;
    }

    public void setNewParticipant(User participants) {
        if (mParticipants != null) {
            mParticipants.add(participants);
            mUserList.add(participants);
        } else {
            mParticipants = new ArrayList<>();
            mParticipants.add(participants);
            mUserList.add(participants);
        }
    }

    public void removeParticipant(User user) {
        mParticipants.remove(user);
        mUserList.remove(user);
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String string) {
        mPlace = null;
        this.mPlace = string;
    }


    @NonNull
    @Override
    public String toString() {
        String time = "";

        if (getSchedule() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            time = sdf.format(getSchedule());
        }
        return getPlace() + " - " + time + " - " + getSubject();
    }

    public static List<User> getUserList() {
        return mUserList;
    }


}
