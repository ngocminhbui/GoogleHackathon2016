package com.example.nguyen.mobilehackthon.Model;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Event {
    private String title;
    private String category;
    private String location;
    private long timeStart;
    String receiver;
    String sender;


    public Event(String title, String category, String location, long timeStart, String receiver, String sender) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.timeStart = timeStart;
        this.receiver = receiver;
        this.sender = sender;
    }

    public Event(){

    }

    public Map<String,String> getEventMap(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("title",this.title);
        map.put("category",this.category);
        map.put("location",this.location);
        map.put("timeStart",String.valueOf(this.timeStart));

        return map;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public long getTimeStart(){
        return timeStart;
    }


    /*public static ArrayList<Event> createContactsList(int numContacts) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < numContacts; i++) {
            events.add(new Event("STT" + i,"Hackthon" ,"SG", 1000));
        }

        return events;
    }*/

}
