package com.example.mareu.Controller;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.Model.Meeting;
import com.example.mareu.Model.User;
import com.example.mareu.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMeetingFragment extends Fragment {

    private static final String TAG ="tag";
    final Calendar myCalendar = Calendar.getInstance();
    private EditText mSubject;
    private Spinner mPlace;
    private EditText mDate;
    private EditText mTime;
    private AppCompatMultiAutoCompleteTextView mParticipant;
    Button mAddNewMeetingButton;
    private Meeting mMeeting=new Meeting();
    public final String STATE_MEETING="meeting";

    public NewMeetingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_new_meeting, container, false);

        if (savedInstanceState!=null) {
            mMeeting = (Meeting) savedInstanceState.getSerializable(STATE_MEETING);
            if (mMeeting.getSchedule()!=null)
                myCalendar.setTime(mMeeting.getSchedule());
        }


        //1 Method to get meeting subject
        configureSubjectEditText();
        //1.1 Method to get meeting date
        configureDateEditText();
        //Method to get meeting time
        configureTimeEditText();
        //Method to configure spinner
        configurePlaceSpinner();
        //Method to get meeting participants
        configureMultiAutoCompleteTextView();
        //Configure Button
        addMeetingButton();

        return v;
    }

    /**
     * Configure meeting Subject
     */
    private void configureSubjectEditText(){
        mSubject=findViewById(R.id.new_meeting_subject);

    }
    private void setMeetingSubject(){
        Log.d(TAG, "setMeetingSubject: "+mSubject.getText());
        if (mSubject.getText()!=null)
            mMeeting.setSubject(mSubject.getText().toString());
        else mMeeting.setSubject(null);
    }
    /**
     * configure DatePicker & show in EditText
     */
    private void configureDateEditText(){
        mDate= findViewById(R.id.new_meeting_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                showTimerPicker();
                mMeeting.setSchedule(myCalendar.getTime());
                updateDateLabel();
            }



        };
        mDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewMeetingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }
    private void updateDateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        mDate.setText(sdf.format(myCalendar.getTime()));
    }
    /**
     * Configure Time Selection
     */
    private void configureTimeEditText(){

        //if time is not filled, we set MILLISECOND=15, if filled, MILLISECOND=0
        myCalendar.set(Calendar.MILLISECOND,15);
        mTime=findViewById(R.id.new_meeting_time);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimerPicker();
            }
        });
    }
    private void showTimerPicker(){
        TimePickerDialog tpd=new TimePickerDialog(NewMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendar.set(Calendar.MINUTE,minute);
                myCalendar.set(Calendar.SECOND,0);
                myCalendar.set(Calendar.MILLISECOND,0);
                mMeeting.setSchedule(myCalendar.getTime());
            }
        },0,0,true);
        tpd.show();
    }
    /**
     * configure spinner
     */
    private void configurePlaceSpinner(){
        mPlace=findViewById(R.id.new_meeting_place_spinner);
        mPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMeeting.setPlace((String)parent.getItemAtPosition(position));
                Log.e(TAG, mMeeting.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMeeting.setPlace((String)parent.getItemAtPosition(0));
            }
        });
    }
    /**
     * Configure participant MultiAutoCompleteTextView
     */
    private void configureMultiAutoCompleteTextView() {

        mParticipant = findViewById(R.id.autocomplete_participant);
        final ArrayAdapter<User> adapter=new ArrayAdapter<User>(this,
                R.layout.item_layout,
                User.generateUserList()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                User user = getItem(position);
                CheckedTextView mCheckedTextView = view.findViewById(R.id.item_checkbox);
                if (mMeeting.getParticipants()!=null) {
                    if (mMeeting.getParticipants().contains(user))
                        mCheckedTextView.setCheckMarkDrawable(R.drawable.ic_remove_circle_outline_24px);
                    else mCheckedTextView.setCheckMarkDrawable(R.drawable.ic_add_circle_24px);
                }
                return view;
            }
        };
        mParticipant.setAdapter(adapter);
        mParticipant.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        //on click listener
        mParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParticipant.showDropDown();
            }
        });

        //onItemClick listener

        mParticipant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user= (User) parent.getItemAtPosition(position);
                editAutoCompletionText(user);
                Log.d(TAG, "onItemClick: "+mMeeting);
            }

        });

    }
    private void editAutoCompletionText(User user){
        if (mMeeting.getParticipants()!=null && mMeeting.getParticipants().contains(user)){
            String str=editString(user);
            mParticipant.setText(str);
            mMeeting.removeParticipant(user);
        }
        else{
            mMeeting.setNewParticipant(user);
        }

    }
    private String editString(User user){

        String str1 = mParticipant.getText().toString();
        String str2 = user.toString()+", ";
        return str1.replace(str2, "");
    }
    /**
     * Configure addMeeting button
     */
    private void addMeetingButton(){
        mAddNewMeetingButton=findViewById(R.id.new_meeting_button);

        mAddNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setMeetingSubject();
                //1.0 we check if all fields are filled and room is empty at this time
                if (mMeeting.getSubject().length()>1
                        && mMeeting.getParticipants()!=null
                        && mMeeting.getSchedule()!=null
                        && mMeeting.getPlace()!=null
                        && !checkForExistingMeeting()
                        //if time is not filled, we set MILLISECOND=15
                        && myCalendar.get(Calendar.MILLISECOND)!=15){
                    // 2.0 if yes and meeting room not taken, finish add meeting to the list and finish activity
                    MainActivity.addMeeting(mMeeting);
                    finish();
                }
                // if not, we search why we can't add meeting
                else {
                    if ( checkForExistingMeeting()){
                        // 2.1 if room already taken, toast to show it to the user
                        Toast.makeText(NewMeetingActivity.this,R.string.room_already_taken, Toast.LENGTH_SHORT)
                                .show();
                    }
                    else if (mMeeting.getSubject().length()==1){
                        // 2.2 if meeting subject is too short
                        Toast.makeText(NewMeetingActivity.this,R.string.meeting_sugject_wrong, Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        // 2.2 if a field is empty, toast to show it to the user
                        Toast.makeText(NewMeetingActivity.this, R.string.Error, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });


    }
    /**
     * @return true if a meeting in the same room at the same time already exist (
     */
    private boolean checkForExistingMeeting(){
        boolean a=checkForExistingMeetingDate();
        boolean b=checkForExistingMeetingPlace();
        if (a && b ){
            return true;
        }
        else return false;
    }
    private boolean checkForExistingMeetingPlace(){
        boolean mCheckPlace=false;
        for (Meeting m:MainActivity.getMeetingList()){
            if (m.getPlace().equals(mMeeting.getPlace())){
                mCheckPlace=true;
                break;
            }
        }
        return mCheckPlace;
    }
    private boolean checkForExistingMeetingDate(){
        boolean mCheckDate=false;
        for (Meeting m:MainActivity.getMeetingList()){
            if (m.getSchedule().compareTo(mMeeting.getSchedule())==0){
                mCheckDate=true;
                break;
            }
        }
        return mCheckDate;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        setMeetingSubject();
        outState.putSerializable(STATE_MEETING,mMeeting);
        super.onSaveInstanceState(outState);
    }

}
