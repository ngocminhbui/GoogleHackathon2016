package com.example.nguyen.mobilehackthon.Widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.nguyen.mobilehackthon.Activity.MainActivity;
import com.example.nguyen.mobilehackthon.Model.DataEvent;
import com.example.nguyen.mobilehackthon.Model.Event;
import com.example.nguyen.mobilehackthon.R;

import java.util.ArrayList;
import java.util.Date;

public class PopResult extends Activity {
    private TextView tvPrintRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_result);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        tvPrintRes = (TextView) findViewById(R.id.tvPrintRes);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        long tmp = getIntent().getLongExtra("time", 0);
        ArrayList<Event> tmpList = MainActivity.getEventInDate(DataEvent.getInstance().getDataEvent(), tmp);

        String tmpRes = "";
        for (int i = 0; i < tmpList.size(); i++){
            Date d = new Date(tmpList.get(i).getTimeStart());
            tmpRes+= d.toString() + " " + tmpList.get(i).getLocation().toString()+ "\n";
        }
        tvPrintRes.setText(tmpRes);
    }
}
