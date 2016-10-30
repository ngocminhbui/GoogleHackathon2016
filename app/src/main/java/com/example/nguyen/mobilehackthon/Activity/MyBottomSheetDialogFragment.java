package com.example.nguyen.mobilehackthon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nguyen.mobilehackthon.R;
import com.example.nguyen.mobilehackthon.Widget.PopAuthor;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    String mString;

    static MyBottomSheetDialogFragment newInstance(String string) {
        MyBottomSheetDialogFragment f = new MyBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        TextView tv = (TextView) v.findViewById(R.id.text);
        Button button1= (Button) v.findViewById(R.id.btn1);
       button1.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
               startActivity(new Intent(MyBottomSheetDialogFragment.this.getContext(), PopAuthor.class));
           }
       });
        return v;
    }
}