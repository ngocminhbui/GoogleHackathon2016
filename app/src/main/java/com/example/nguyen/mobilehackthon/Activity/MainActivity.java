package com.example.nguyen.mobilehackthon.Activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.nguyen.mobilehackthon.Fragment.ListViewFragment;
import com.example.nguyen.mobilehackthon.Fragment.MapViewFragment;
import com.example.nguyen.mobilehackthon.Helper.FirebaseHelper;
import com.example.nguyen.mobilehackthon.Helper.PreferenceHelper;
import com.example.nguyen.mobilehackthon.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton fabChangeView, fabAddEvent;
    private PreferenceHelper preferenceHelper;

    private LinearLayout layout;
    private RelativeLayout main;
    // flag = true => listfragment, flag = false => mapfragment
    private  boolean flag;

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

    }

    private void changeFragment(Fragment tmpFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, tmpFragment);
        ft.addToBackStack(null);
        ft.commit();
        flag = !flag;
    }

}
