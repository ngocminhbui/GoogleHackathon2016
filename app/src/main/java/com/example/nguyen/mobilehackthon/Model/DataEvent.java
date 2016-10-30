package com.example.nguyen.mobilehackthon.Model;

import java.util.ArrayList;
import java.util.List;

public class DataEvent {
    private static DataEvent ourInstance = new DataEvent();
    private static ArrayList<Event> listEvent = new ArrayList<Event>();

    public static DataEvent getInstance() {
        return ourInstance;
    }


    private DataEvent(){
        listEvent = new ArrayList<>();
    }


    public ArrayList<Event> getDataEvent(){
        return listEvent;
    }

}
