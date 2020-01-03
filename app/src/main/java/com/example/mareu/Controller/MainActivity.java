package com.example.mareu.Controller;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.Utils.CustomDateFilterDialogClass;
import com.example.mareu.Utils.CustomRoomFilterDialogBox;
import com.example.mareu.Utils.FilterEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static List<Meeting> mMeetingList = new ArrayList<>();
    Context context = MainActivity.this;
    private static List<Meeting> mSortedList;
    private CreatedMeetingFragment mCreatedMeetingFragment;
    private Button mDismissButton;


    /**
     * Add, remove and get meeting
     */

    public static void addMeeting(Meeting meeting) {
        mMeetingList.add(meeting);
    }

    public static void removeMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }

    public static List<Meeting> getMeetingList() {
        if (mSortedList!=null)
            return mSortedList;
        else
            return mMeetingList;
    }

    public static void setSortedMeetingList(List<Meeting> list){
        mSortedList=list;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        configureFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        //Init meeting list
        //configure floating action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mNewMeetingActivity = new Intent(MainActivity.this, NewMeetingActivity.class);
                startActivity(mNewMeetingActivity);
            }


        });
        configureDismissButton();
        configureFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter_by_date) {
            configureDateFilter();
        }
        if (id==R.id.filter_by_room){
            configureRoomFilter();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment configuration
     */

    public void configureFragment() {
        mCreatedMeetingFragment = new CreatedMeetingFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_main, mCreatedMeetingFragment)
                .commit();
    }

    /**
     * Dialog boxes for filters configuration
     */

    private void configureDateFilter(){
        CustomDateFilterDialogClass cd = new CustomDateFilterDialogClass(context);
        cd.show();
    }

    private void configureRoomFilter(){
        CustomRoomFilterDialogBox cd = new CustomRoomFilterDialogBox(context);
        cd.show();
    }

    /**
     * dismiss button configuration
     */

    private void configureDismissButton(){
        mDismissButton=findViewById(R.id.dismiss_button);
        if (mSortedList==null){
            mDismissButton.setVisibility(View.GONE);
        }
        else{
            mDismissButton.setVisibility(View.VISIBLE);
        }

        mDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSortedList=null;
                EventBus.getDefault().post(new FilterEvent());
            }
        });
    }

    /**
     * EventBus configuration to update RecyclerView when filters are applied
     */



    @Subscribe
    public void onFilterEvent(FilterEvent event){
        configureDismissButton();
    }

}
