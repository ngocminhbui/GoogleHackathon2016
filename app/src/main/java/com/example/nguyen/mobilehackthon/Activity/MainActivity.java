package com.example.nguyen.mobilehackthon.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nguyen.mobilehackthon.Fragment.ListViewFragment;
import com.example.nguyen.mobilehackthon.Fragment.MapViewFragment;
import com.example.nguyen.mobilehackthon.Helper.FirebaseHelper;
import com.example.nguyen.mobilehackthon.Helper.PreferenceHelper;
import com.example.nguyen.mobilehackthon.Helper.TimeHelper;
import com.example.nguyen.mobilehackthon.Model.DataEvent;
import com.example.nguyen.mobilehackthon.Model.Event;
import com.example.nguyen.mobilehackthon.R;
import com.example.nguyen.mobilehackthon.Widget.PopResult;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton fabChangeView, fabAddEvent;
    private PreferenceHelper preferenceHelper;

    private LinearLayout layout;
    private RelativeLayout main;
    // flag = true => listfragment, flag = false => mapfragment
    private  boolean flag;
    private ImageButton btnRecord;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);

        layout = (LinearLayout) findViewById(R.id.progressbar_view);
        main = (RelativeLayout) findViewById(R.id.MainLayout);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        fabChangeView = (FloatingActionButton) findViewById(R.id.fab_change_view);
        fabAddEvent = (FloatingActionButton) findViewById(R.id.fab_add_event);
        btnRecord = (ImageButton) findViewById(R.id.btnRecord);
        layout.setVisibility(View.GONE);

        String id = preferenceHelper.getString("id");
        FirebaseHelper.getUserPropRef(id,"name").setValue(preferenceHelper.getString("name"));
        FirebaseHelper.getUserPropRef(id,"avatar").setValue(preferenceHelper.getString("avatar"));

        //Init View
        flag = false;
        changeFragment(new ListViewFragment());

        fabChangeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (flag)
                    changeFragment(new MapViewFragment());
                else
                    changeFragment(new ListViewFragment());
            }
        });

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });


        /*------------------ Duy code ---------------*/
        final BottomSheetDialogFragment myBottomSheet = MyBottomSheetDialogFragment.newInstance("Modal Bottom Sheet");
        ImageButton btnNavbar = (ImageButton) findViewById(R.id.btnNavBar);
        btnNavbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());

            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a date to see events");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"MIC NOT SUPPORTED",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String words = result.get(0);

                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(words);
                    String dateTime = "";

                    while (m.find())
                        dateTime += m.group() + "-";

                    Date d1 = new Date();
                    try {
                        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
                        d1 = dFormat.parse(dateTime);
                    } catch (ParseException e) {
                        d1 = new Date();
                    }
                    Intent intent = new Intent(MainActivity.this, PopResult.class);
                    intent.putExtra("time", d1.getTime());
                    startActivity(intent);

                    break;
                }



            }
        }
    }

    private void changeFragment(Fragment tmpFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, tmpFragment);
        ft.addToBackStack(null);
        ft.commit();
        flag = !flag;
    }



    public static ArrayList<Event> getEventInDate(ArrayList<Event> events, long date) {
        ArrayList<Event> dayEvents = new ArrayList<Event>();
        Date now = new Date();
        for(int i=0;i<events.size();i++){

            if (new TimeHelper().dateDiff(date)==0){
                dayEvents.add(events.get(i));
            }
        }
        return dayEvents;
    }

}
