package com.example.mareu.Utils;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Controller.MainActivity;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MeetingViewHolder> {

    private List<Meeting> mMeetingList;


    public MeetingRecyclerViewAdapter(List<Meeting> meetingList) {
        this.mMeetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_meeting_layout, parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.updateWithMeetingInfo(this.mMeetingList.get(position));

    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }


    class MeetingViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mMeetingResume;
        private TextView mMeetingParticipants;
        private ImageButton mDeleteButton;

         MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.left_ImageView);
            mMeetingResume = itemView.findViewById(R.id.fragment_meeting_TextView_Top);
            mMeetingParticipants = itemView.findViewById(R.id.fragment_meeting_TextView_Bottom);
            mDeleteButton = itemView.findViewById(R.id.delete_button);

        }

         void updateWithMeetingInfo(final Meeting meeting) {
            mMeetingResume.setText(meeting.toString());
            mMeetingParticipants.setText(meeting.getParticipants().toString());
            mImageView.setBackgroundColor(Color.CYAN);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.removeMeeting(meeting);
                    EventBus.getDefault().post(new FilterEvent());
                }
            });
        }
    }
}
