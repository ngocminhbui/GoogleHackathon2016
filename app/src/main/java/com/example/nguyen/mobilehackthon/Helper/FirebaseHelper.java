package com.example.nguyen.mobilehackthon.Helper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseHelper {

    //Get user event
    public static DatabaseReference getUserRef(String id){
        return FirebaseDatabase.getInstance().getReference().child("users/"+id);
    }

    public static DatabaseReference getUserPropRef(String id, String properties){
        return FirebaseDatabase.getInstance().getReference().child("users/"+id+"/"+properties);
    }


    public static DatabaseReference getEventRef() {
        return FirebaseDatabase.getInstance().getReference().child("events/");
    }
}

