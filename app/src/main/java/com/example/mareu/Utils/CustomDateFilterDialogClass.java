package com.example.mareu.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.mareu.Controller.MainActivity;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CustomDateFilterDialogClass extends Dialog {

    final Calendar myCalendarFrom = Calendar.getInstance();
    final Calendar myCalendarTo = Calendar.getInstance();

    private EditText mFromDate;
    private EditText mToDate;
    private TextView mOk;
    private TextView mCancel;

    public CustomDateFilterDialogClass(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_date);
        mFromDate=findViewById(R.id.from_date);
        mToDate=findViewById(R.id.to_date);
        mOk=findViewById(R.id.TextButton_ok);
        mCancel=findViewById(R.id.TextButton_cancel);

        //configure the pickers to get From information
        configureDateFromEditText();
        //configure the pickers to get To information
        configureDateToEditText();

        // Ok and Cancel Button configuration

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCalendarTo.getTime().compareTo(myCalendarFrom.getTime())<1)
                    Toast.makeText(getContext(),"2nd date must be after 1st date", Toast.LENGTH_LONG).show();
                else{
                    sortListByDates(myCalendarFrom,myCalendarTo);
                    dismiss();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    /**
     * Configure Date from
     */


    private void configureDateFromEditText(){


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarFrom.set(Calendar.YEAR, year);
                myCalendarFrom.set(Calendar.MONTH, monthOfYear);
                myCalendarFrom.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                showTimerPicker();
                updateDateLabel();
            }



        };

        mFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendarFrom
                        .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                        myCalendarFrom.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yy hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);
        mFromDate.setText(sdf.format(myCalendarFrom.getTime()));
    }

    private void showTimerPicker(){

        TimePickerDialog tpd=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendarFrom.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendarFrom.set(Calendar.MINUTE,minute);
                myCalendarFrom.set(Calendar.SECOND,0);
                myCalendarFrom.set(Calendar.MILLISECOND,0);
            }
        },0,0,true);
        tpd.show();

    }

    /**
     * Configure Date to
     */

    private void configureDateToEditText(){


        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                showTimeToPicker();
                updateDateToLabel();
            }



        };

        mToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    private void updateDateToLabel() {
        String myFormat = "dd/MM/yy mm:hh"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);
        mToDate.setText(sdf.format(myCalendarTo.getTime()));
    }

    private void showTimeToPicker(){

        TimePickerDialog tpdg=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendarTo.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendarTo.set(Calendar.MINUTE,minute);
                myCalendarTo.set(Calendar.SECOND,0);
                myCalendarTo.set(Calendar.MILLISECOND,0);
            }
        },0,0,true);
        tpdg.show();

    }

    /**
     * Sort meeting list with date argument
     */

    public void sortListByDates(Calendar calendarFrom, Calendar calendarTo){
        List<Meeting> sortedList=new ArrayList<>();

        for (Meeting meeting: MainActivity.getMeetingList()) {
            if (meeting.getSchedule().compareTo(calendarFrom.getTime()) > 0
                    && meeting.getSchedule().compareTo(calendarTo.getTime()) < 0)
                sortedList.add(meeting);
        }
        MainActivity.setSortedMeetingList(sortedList);
        if (sortedList.size()==0) {
            Toast.makeText(getContext(), "No meeting at those dates", Toast.LENGTH_SHORT).show();
            MainActivity.setSortedMeetingList(null);
        }
        

    }

}
