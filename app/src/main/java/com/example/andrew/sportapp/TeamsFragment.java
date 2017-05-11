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
    public static final String W_HOCKEY_ID = "Women's Hockey";

    Button mensHockey=null;
    public static final String M_HOCKEY_ID = "Men's Hockey";

    Button womens_lax=null;
    public static final String W_LAX_ID = "Women's Lacrosse";

    Button mens_lax=null;
    public static final String M_LAX_ID = "Men's Lacrosse";

    public TeamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teams, container, false);

        womens_lax = (Button) v.findViewById(R.id.womensLacrosse);
        mens_lax = (Button) v.findViewById(R.id.mensLacrosse);
        mensHockey = (Button) v.findViewById(R.id.mensHockey);
        womensHockey = (Button) v.findViewById(R.id.womensHockey);

        womens_lax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(W_LAX_ID);
            }
        });

        mens_lax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(M_LAX_ID);
            }
        });

        mensHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(M_HOCKEY_ID);
            }
        });

        womensHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTeamsActivity(W_HOCKEY_ID);
            }
        });

        return v;
    }
    public void sendToTeamsActivity(String team){
        MainActivity.haveIntent = true;

        Intent myIntent = new Intent(getActivity(), teamsActivity.class);
        myIntent.putExtra("team", team);
        startActivity(myIntent);
    }

}
