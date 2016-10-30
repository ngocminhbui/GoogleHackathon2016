package com.example.nguyen.mobilehackthon.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nguyen.mobilehackthon.Adapter.GooglePlacesAutocompleteAdapter;
import com.example.nguyen.mobilehackthon.Helper.PreferenceHelper;
import com.example.nguyen.mobilehackthon.Model.Event;
import com.example.nguyen.mobilehackthon.R;
import com.example.nguyen.mobilehackthon.Widget.PopupCalendar;
import com.example.nguyen.mobilehackthon.Widget.PopupClock;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    private EditText edtTitle, edtTen, edtSdt, edtTk;
    private AutoCompleteTextView edtLocation;
    private ImageButton btnLocation, btnPickDayStart, btnPickDayEnd, btnPickTimeStart, btnPickTimeEnd, btnAddParticipant,btnBack;
    private Button btnFinish;
    private TextView tvDay1, tvMonth1, tvYear1, tvDay2, tvMonth2, tvYear2;
    private TextView tvHour1,tvHour2,tvMin1,tvMin2;
    final int RQ_PICK_DATE1 = 111;
    final int RQ_PICK_DATE2 = 112;
    final int RQ_PICK_TIME1 = 101;
    final int RQ_PICK_TIME2 = 301;

    public AddEventActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initView();
        listener();
    }

    private void listener() {
        btnAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTen.getVisibility() != View.VISIBLE) {
                    edtTen.setVisibility(View.VISIBLE);
                    edtTk.setVisibility(View.VISIBLE);
                    edtSdt.setVisibility(View.VISIBLE);
                } else {
                    edtTen.setVisibility(View.GONE);
                    edtTk.setVisibility(View.GONE);
                    edtSdt.setVisibility(View.GONE);
                }

            }
        });

        btnPickDayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEventActivity.this, PopupCalendar.class);
                startActivityForResult(intent, RQ_PICK_DATE1);
            }
        });
        btnPickTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEventActivity.this, PopupClock.class);
                startActivityForResult(intent, RQ_PICK_TIME1);
            }
        });
        btnPickDayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEventActivity.this, PopupCalendar.class);
                startActivityForResult(intent, RQ_PICK_DATE2);
            }
        });
        btnPickTimeEnd.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEventActivity.this, PopupClock.class);
                startActivityForResult(intent, RQ_PICK_TIME2);
            }
        }));
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=edtTitle.getText().toString();
                String category="công việc";
                String location= edtLocation.getText().toString();
                long timeStart= creatDate(tvDay1.getText().toString(), tvMonth1.getText().toString(), tvYear1.getText().toString(),
                        tvHour1.getText().toString(), tvMin1.getText().toString());
                String receiver="";
                PreferenceHelper preferenceHelper = new PreferenceHelper(AddEventActivity.this);
                String sender= preferenceHelper.getString("id");
                Event event = new Event(title,category,location,timeStart,receiver,sender);

                sendEvent(sender,receiver,event);

                startActivity(new Intent(AddEventActivity.this,MainActivity.class));
                finish();
            }
        });

        edtLocation.setThreshold(1);
        edtLocation.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.custom_auto_item, R.id.tvAutoItem));
    }

    public static void sendEvent(String senderID, String receiverID, Event e){
        DatabaseReference eventRef=FirebaseDatabase.getInstance().getReference().child("events");
        DatabaseReference sendRef=FirebaseDatabase.getInstance().getReference().child("users/"+senderID+"/events");
        DatabaseReference revRef= FirebaseDatabase.getInstance().getReference().child("users/"+receiverID+"/events");

        String eventID = eventRef.push().getKey();

        Map<String,String> dict = e.getEventMap();
        dict.put("sender",senderID);
        dict.put("receiver",receiverID);
        eventRef.child(eventID).setValue(dict);

        Map<String,String> sendDict = new HashMap<String,String>();
        Map<String,String> revDict = new HashMap<String,String>();
        sendDict.put(eventID,"1"); //sender accepted the meeting by default
        revDict.put(eventID,"0"); //revceiver hasn't agreed yet
        sendRef.setValue(sendDict);
        revRef.setValue(revDict);
    }


    private void initView() {
        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtLocation = (AutoCompleteTextView) findViewById(R.id.edt_location);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtSdt = (EditText) findViewById(R.id.edtSdt);
        edtTk = (EditText) findViewById(R.id.edt_tk);
        btnPickDayStart = (ImageButton) findViewById(R.id.btn_pick_day_start);
        btnPickDayEnd = (ImageButton) findViewById(R.id.btn_pick_day_end);
        btnPickTimeStart = (ImageButton) findViewById(R.id.btn_pick_time);
        btnPickTimeEnd = (ImageButton) findViewById(R.id.btn_pick_time_end);
        btnAddParticipant = (ImageButton) findViewById(R.id.btn_add_participant);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        tvDay1 = (TextView) findViewById(R.id.tv_day);
        tvDay2= (TextView) findViewById(R.id.tv_day_end);
        tvMonth1=(TextView) findViewById(R.id.tv_month);
        tvMonth2=(TextView) findViewById(R.id.tv_month_end);
        tvYear1=(TextView) findViewById(R.id.tv_year);
        tvYear2=(TextView) findViewById(R.id.tv_year_end);
        tvHour1=(TextView) findViewById(R.id.tv_gio);
        tvHour2=(TextView) findViewById(R.id.tv_gio_end);
        tvMin1=(TextView) findViewById(R.id.tv_phut);
        tvMin2=(TextView) findViewById(R.id.tv_phut_end);
        btnBack= (ImageButton) findViewById(R.id.btn_back);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_PICK_DATE1 && resultCode == RESULT_OK) {

            tvDay1.setText(data.getStringExtra("day"));
            tvMonth1.setText(data.getStringExtra("month"));
            tvYear1.setText(data.getStringExtra("year"));
        }
        if (requestCode == RQ_PICK_DATE2 && resultCode == RESULT_OK) {
            tvDay2.setText(data.getStringExtra("day"));
            tvMonth2.setText(data.getStringExtra("month"));
            tvYear2.setText(data.getStringExtra("year"));
        }
        if (requestCode==RQ_PICK_TIME1 && resultCode==RESULT_OK){
            tvHour1.setText(data.getStringExtra("gio"));
            tvMin1.setText(data.getStringExtra("phut"));
        }

        if (requestCode==RQ_PICK_TIME2 && resultCode==RESULT_OK){
            tvHour2.setText(data.getStringExtra("gio"));
            tvMin2.setText(data.getStringExtra("phut"));
        }
    }

    static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy-MM-dd HH:mm:ss");
    long creatDate(String dd,String mm,String yy,String hh,String ms)
    {
        String dateFirst=yy+"-"+mm+"-"+dd+" "+hh+":"+ms+":00";

        // SimpleDateFormat dates = new SimpleDateFormat();

        try {
            //thisDate.getTime()
            Date thisDate;

            thisDate=sdf.parse(dateFirst);

            return thisDate.getTime();
        } catch (Exception exception) {
            // TODO Auto-generated catch block

        }


        return 0;
    }
}
