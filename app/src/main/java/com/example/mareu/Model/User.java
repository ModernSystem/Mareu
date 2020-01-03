package com.example.mareu.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User implements Serializable {

    private String mName;
    private String mMail;
    private int id;

    /**
     *Constructors
     */

    public User(){

    }

    private User(int id,String name,String mail){
        this.id=id;
        this.mName=name;
        this.mMail=mail;
    }

    /**
     *Getters and Setters
     */

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    private String getMail() {
        return mMail;
    }

    public int getId(){
        return id;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    private static List<User> FAKE_USER_LIST= Arrays.asList(
            new User(0,"Maxime","maxime@lamzone.fr"),
            new User(1,"Jean","Jean@lamzone.fr"),
            new User(2,"Patrick","Patrick@lamzone.fr"),
            new User(3,"Marilou","Marilou@lamzone.fr"),
            new User(4,"Jeanne","Jeanne@lamzone.fr"),
            new User(5,"Etienne","Etienne@lamzone.fr"),
            new User(6,"Romy","Romy@lamzone.fr"),
            new User(7,"Alan","Alan@lamzone.fr"),
            new User(8,"Bob","Bob@lamzone.fr"),
            new User(9,"Helene","Helene@lamzone.fr"));

    public static List<User> generateUserList(){
        return FAKE_USER_LIST;
    }

    @NonNull
    @Override
    public String toString() {
        return getName()+" ("+getMail()+")";
    }
}
