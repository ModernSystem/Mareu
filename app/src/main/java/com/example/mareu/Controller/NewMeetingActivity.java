package com.example.mareu.Controller;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.mareu.R;

public class NewMeetingActivity extends AppCompatActivity {

    private NewMeetingFragment mNewMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mNewMeetingFragment=new NewMeetingFragment();
        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_new_meeting, mNewMeetingFragment,"fragment")
                    .commit();
        }
    }

