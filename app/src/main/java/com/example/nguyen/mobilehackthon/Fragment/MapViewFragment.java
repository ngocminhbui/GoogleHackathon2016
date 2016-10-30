package com.example.nguyen.mobilehackthon.Fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyen.mobilehackthon.Helper.LocationHelper;
import com.example.nguyen.mobilehackthon.Helper.TimeHelper;
import com.example.nguyen.mobilehackthon.Model.DataEvent;
import com.example.nguyen.mobilehackthon.Model.Event;
import com.example.nguyen.mobilehackthon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Event> listEvent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container,false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listEvent = DataEvent.getInstance().getDataEvent();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        LocationHelper locationHelper = new LocationHelper(getContext());
        LatLng curPoint = new LatLng(locationHelper.getLocation().latitude, locationHelper.getLocation().longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(curPoint));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(locationHelper.MAP_ZOOM));

        for (int i = 0; i < listEvent.size(); i++){
            LatLng tmpPoint = new LocationHelper(getContext()).getLatLng(listEvent.get(i).getLocation());

            BitmapDescriptor tmpIcon = null;
            String tmp = new TimeHelper().getDay(listEvent.get(i).getTimeStart());
            Log.d("ASD", String.valueOf(listEvent.get(i).getTimeStart()));
            Log.d("DSASDA", tmp);
            if (tmp.equals("Mon")||tmp.equals("T2"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_t2);
            else
            if (tmp.equals("Tue")||tmp.equals("T3"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_t3);
            if (tmp.equals("Wed")||tmp.equals("T4"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_t4);
            else
            if (tmp.equals("Thu")||tmp.equals("T5"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_t5);
            else
            if (tmp.equals("Fri")||tmp.equals("T6"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_t6);
            else
            if (tmp.equals("Sat")||tmp.equals("T7"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_t7);
            else         if (tmp.equals("Sun")||tmp.equals("CN"))
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_cn);
            else
                tmpIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

            MarkerOptions tmpMarker = new MarkerOptions().position(tmpPoint).icon(tmpIcon);
            mMap.addMarker(tmpMarker);
        }
    }
}
