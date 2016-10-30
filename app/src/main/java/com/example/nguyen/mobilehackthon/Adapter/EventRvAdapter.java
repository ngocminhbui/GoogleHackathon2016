package com.example.nguyen.mobilehackthon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyen.mobilehackthon.Helper.FirebaseHelper;
import com.example.nguyen.mobilehackthon.Helper.TimeHelper;
import com.example.nguyen.mobilehackthon.Model.DataEvent;
import com.example.nguyen.mobilehackthon.Model.Event;
import com.example.nguyen.mobilehackthon.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventRvAdapter  extends RecyclerView.Adapter<EventRvAdapter.ViewHolder>{
    private ArrayList<String> listEventId;
    private ArrayList<Event> listEvent;
    private String userId;
    private ChildEventListener eventListener;
    private Context mContext;

    private void addChild(String previousChildName,Event event,String key) {
        // Insert into the correct location, based on previousChildName
        if (previousChildName == null) {
            listEvent.add(0, event);
            listEventId.add(0, key);
        } else {
            int previousIndex = listEventId.indexOf(previousChildName);
            int nextIndex = previousIndex + 1;
            if (nextIndex == listEvent.size()) {
                listEvent.add(event);
                listEventId.add(key);
            } else {
                listEvent.add(nextIndex, event);
                listEventId.add(nextIndex, key);
            }
        }
        notifyDataSetChanged();
    }

    public EventRvAdapter(final ArrayList<Event> listEvent,final ArrayList<String> listEventId, String userId, Context mContext ){
        this.listEvent = listEvent;
        this.listEventId = listEventId;
        this.userId = userId;
        this.mContext = mContext;

        eventListener = FirebaseHelper.getEventRef()
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Event event = getEventFromFirebase(dataSnapshot);
                        DataEvent.getInstance().getDataEvent().add(event);
                        String key = dataSnapshot.getKey();
                        addChild(s,event,key);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        String key = dataSnapshot.getKey();
                        Event event = getEventFromFirebase(dataSnapshot);
                        int index = listEventId.indexOf(key);
                        listEvent.set(index, event);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();
                        int index = listEventId.indexOf(key);
                        listEvent.remove(index);
                        listEventId.remove(index);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        String key = dataSnapshot.getKey();
                        Event event = getEventFromFirebase(dataSnapshot);
                        int index = listEventId.indexOf(key);
                        listEvent.remove(index);
                        listEventId.remove(index);
                        addChild(s,event,key);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //no-op
                    }
                });

    }

    private Event getEventFromFirebase(DataSnapshot dataSnapshot) {

        String title = dataSnapshot.child("title").getValue().toString();
        String category = dataSnapshot.child("category").getValue().toString();
        String location = dataSnapshot.child("location").getValue().toString();
        long timeStart = Long.parseLong(dataSnapshot.child("timeStart").getValue().toString());

        Event event = new Event(title,category,location,timeStart,userId,"");

        return event;
    }


    @Override
    public EventRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_event_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = listEvent.get(position);
        holder.tvTitle.setText(event.getTitle());
        holder.tvCategory.setText(event.getCategory());
        holder.tvLocation.setText(event.getLocation());

        Drawable drawable;
        switch (new TimeHelper().dateDiff(event.getTimeStart())){
            case 0:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft_today);
                break;
            case 1:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft1);
                break;
            case 2:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft2);
                break;
            case 3:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft3);
                break;
            case 4:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft4);
                break;
            case 5:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft5);
                break;
            default:
                drawable =  mContext.getResources().getDrawable(R.drawable.ic_dayleft5_more);
                break;

        }

        holder.icDayLeft.setImageDrawable(drawable);

    }


    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        public TextView tvTitle;
        public TextView tvCategory;
        public TextView tvLocation;
        public ImageView icDayLeft;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
            icDayLeft = (ImageView) itemView.findViewById(R.id.img_count_day_left);

            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                        Intent dial = new Intent(Intent.ACTION_DIAL);
                        dial.setData(Uri.parse("tel:" + "01216983172"));
                        view.getContext().startActivity(dial);
                }
            });
        }
    }
}
