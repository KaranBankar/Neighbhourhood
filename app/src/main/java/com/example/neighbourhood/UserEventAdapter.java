package com.example.neighbourhood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.neighbourhood.R;
import com.example.neighbourhood.UserEvent;
import java.util.List;

public class UserEventAdapter extends RecyclerView.Adapter<UserEventAdapter.UserEventViewHolder> {

    private List<UserEvent> eventList;

    public UserEventAdapter(List<UserEvent> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public UserEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event2, parent, false);
        return new UserEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEventViewHolder holder, int position) {
        UserEvent event = eventList.get(position);
        holder.eventName.setText(event.getName());
        holder.eventDescription.setText(event.getDescription());
        holder.eventDate.setText("Date: " + event.getDate());
        holder.eventTime.setText("Time: " + event.getTime());
        holder.eventUserName.setText("User: " + event.getUserName());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class UserEventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventDescription, eventDate, eventTime, eventUserName;

        public UserEventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventDate = itemView.findViewById(R.id.event_date);
            eventTime = itemView.findViewById(R.id.event_time);
            eventUserName = itemView.findViewById(R.id.event_user_name);
        }
    }
}
