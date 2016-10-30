package com.example.nguyen.mobilehackthon.Widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.nguyen.mobilehackthon.R;


public class PopAuthor extends  Activity{
   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_author);

       DisplayMetrics dm = new DisplayMetrics();
       getWindowManager().getDefaultDisplay().getMetrics(dm);

       int width = dm.widthPixels;
       int height = dm.heightPixels;

       getWindow().setLayout((int) (width * .8), (int) (height * .8));
    }

}
