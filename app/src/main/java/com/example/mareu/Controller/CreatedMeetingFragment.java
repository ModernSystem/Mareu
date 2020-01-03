package com.example.mareu.Controller;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.Utils.FilterEvent;
import com.example.mareu.Utils.MeetingRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatedMeetingFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public CreatedMeetingFragment() {
        // Required empty public constructor
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

    @Subscribe
    public void onFilterEvent(FilterEvent event){
        initRecyclerView();
        Log.d(TAG, "onFilterEvent: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_created_meeting, container, false);
        mRecyclerView=view.findViewById(R.id.fragment_meeting_Recycler_View);
        initRecyclerView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }

    private void initRecyclerView(){
        RecyclerView.Adapter adapter= new MeetingRecyclerViewAdapter(MainActivity.getMeetingList());
        mRecyclerView.setAdapter(adapter);
    }




}

