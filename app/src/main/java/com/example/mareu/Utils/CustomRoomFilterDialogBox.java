package com.example.mareu.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mareu.Controller.MainActivity;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CustomRoomFilterDialogBox extends Dialog {

    private CheckBox mRoomA;
    private CheckBox mRoomB;
    private CheckBox mRoomC;
    private TextView mOk;
    private TextView mCancel;
    private ArrayList<String> mSelectedRoom=new ArrayList<>();


    public CustomRoomFilterDialogBox(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_room);

        //Room A checkbox configuration
        mRoomA=findViewById(R.id.filter_room_A);
        mRoomA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSelectedRoom.contains("Room A")&& !isChecked){
                    mSelectedRoom.remove("Room A");
                }
                if (isChecked)
                    mSelectedRoom.add("Room A");
            }
        });
        //Room B checkbox configuration
        mRoomB=findViewById(R.id.filter_room_B);
        mRoomB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSelectedRoom.contains("Room B")&& !isChecked){
                    mSelectedRoom.remove("Room B");
                }
                if (isChecked)
                    mSelectedRoom.add("Room B");
            }
        });
        //Room C checkbox configuration
        mRoomC=findViewById(R.id.filter_room_C);
        mRoomC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSelectedRoom.contains("Room C")&& !isChecked){
                    mSelectedRoom.remove("Room C");
                }
                if (isChecked)
                    mSelectedRoom.add("Room C");
            }
        });
        mOk=findViewById(R.id.textbutton_ok_filter_room);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedRoom.size()==0)
                    Toast.makeText(getContext(),"Please select rooms", Toast.LENGTH_LONG).show();
                else{
                    sortListByRooms();
                    EventBus.getDefault().post(new FilterEvent());
                    dismiss();
                }
            }
        });
        mCancel=findViewById(R.id.textbutton_cancel_filter_room);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void sortListByRooms(){
        List<Meeting> sortedList=new ArrayList<>();
        for (Meeting meeting: MainActivity.getMeetingList()) {
            if (mSelectedRoom.contains(meeting.getPlace()))
                sortedList.add(meeting);
        }
        MainActivity.setSortedMeetingList(sortedList);
        if (sortedList.size()==0) {
            Toast.makeText(getContext(), "No meeting at those dates", Toast.LENGTH_SHORT).show();
            MainActivity.setSortedMeetingList(null);
        }
    }
}
