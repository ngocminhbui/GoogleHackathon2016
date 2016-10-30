package com.example.nguyen.mobilehackthon.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyen.mobilehackthon.Adapter.EventRvAdapter;
import com.example.nguyen.mobilehackthon.Helper.PreferenceHelper;
import com.example.nguyen.mobilehackthon.Model.Event;
import com.example.nguyen.mobilehackthon.R;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {

    private ArrayList<Event> listEvent;
    private ArrayList<String> listEventId;
    private RecyclerView rvListEvent;
    private PreferenceHelper preferenceHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        preferenceHelper = new PreferenceHelper(getActivity());

        rvListEvent = (RecyclerView) view.findViewById(R.id.rv_list_event);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvListEvent.setLayoutManager(llm);

        //listEvent = Event.createContactsList(20);
        listEvent = new ArrayList<>();
        listEventId = new ArrayList<>();
        EventRvAdapter adapter = new EventRvAdapter(listEvent,listEventId,preferenceHelper.getString("id"), getContext());
        rvListEvent.setAdapter(adapter);
        rvListEvent.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


}
