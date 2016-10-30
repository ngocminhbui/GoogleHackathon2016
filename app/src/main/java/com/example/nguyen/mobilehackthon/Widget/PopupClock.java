package com.example.nguyen.mobilehackthon.Widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.nguyen.mobilehackthon.Activity.AddEventActivity;
import com.example.nguyen.mobilehackthon.R;

/**
 * Created by khangduyle on 29/10/2016.
 */
public class PopupClock extends Activity{
    private TimePicker timePicker1;
    int hh,mm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_pick_time);

        DisplayMetrics dm1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm1);

        int width = dm1.widthPixels;
        int height = dm1.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        timePicker1= (TimePicker) findViewById(R.id.time);

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Button bt=(Button) findViewById(R.id.btn_time_ok);
                hh=i;
                mm=i1;
                bt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(PopupClock.this, AddEventActivity.class);
                        intent.putExtra("gio",Integer.toString(hh));
                        intent.putExtra("phut",Integer.toString(mm));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

            }
        });

    }
    public void setTime(View view)
    {


    }

}
