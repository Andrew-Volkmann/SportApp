package com.example.andrew.sportapp;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamsFragment extends Fragment {

    Button womensHockey= null;
    public static final String WHOCKEYID = "Women's Hockey";

    Button mensHockey=null;
    public static final String MHOCKEYID = "Men's Hockey";

    Button womensBasketball=null;
    public static final String WBASKETBALLID = "Women's Basketball";

    Button mensBasketball=null;
    public static final String MBASKETBALLID = "Men's Basketball";

    public TeamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teams, container, false);

        womensBasketball = (Button) v.findViewById(R.id.womensBasketBall);
        mensBasketball = (Button) v.findViewById(R.id.mensBasketball);
        mensHockey = (Button) v.findViewById(R.id.mensHockey);
        womensHockey = (Button) v.findViewById(R.id.womensHockey);

        womensBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(WBASKETBALLID);
            }
        });

        mensBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(MBASKETBALLID);
            }
        });

        mensHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(MHOCKEYID);
            }
        });

        womensHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(WHOCKEYID);
            }
        });

        return v;
    }
    public void sendToTeamsActivity(String team){

        Intent myIntent = new Intent(getActivity(), teamsActivity.class);
        myIntent.putExtra("team", team);
        startActivity(myIntent);
    }

}
