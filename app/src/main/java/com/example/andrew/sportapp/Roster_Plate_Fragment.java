package com.example.andrew.sportapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Roster_Plate_Fragment extends Fragment {
    private String [] data;

    private TextView number = null;
    private TextView name = null;
    private TextView position = null;
    private TextView year = null;

    public Roster_Plate_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_roster__plate_, container, false);
        number = (TextView) v.findViewById(R.id.number);
        name = (TextView)v.findViewById(R.id.name);
        position = (TextView) v.findViewById(R.id.postion);
        year = (TextView) v.findViewById(R.id.year);

        number.setText(data[0]);
        name.setText(data[1]);
        position.setText(data[3]);
        year.setText(data[2]);

        return v;
    }
    //                   0         1         2   3
    // data comes in as 5, Kirsten Padalis, Sr., D

    public void populate(String[] data){
        this.data = data;

    }
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }



}
