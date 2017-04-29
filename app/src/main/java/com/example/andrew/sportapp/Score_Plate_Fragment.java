package com.example.andrew.sportapp;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




public class Score_Plate_Fragment extends Fragment {

    TextView sport_name = null;
    TextView home_team = null;
    TextView away_team = null;
    TextView home_score = null;
    TextView away_score = null;
    TextView result = null;
    String [] data = null;



    public Score_Plate_Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_score__plate_, container, false);
        sport_name = (TextView) v.findViewById(R.id.sport_name);
        sport_name.setText(data[0]);
        home_team = (TextView) v.findViewById(R.id.home_team_name);
        away_team = (TextView) v.findViewById(R.id.away_team_name);
        home_score = (TextView) v.findViewById(R.id.home_team_score);
        away_score = (TextView) v.findViewById(R.id.away_team_score);
        result = (TextView) v.findViewById(R.id.game_progress);
        result.setText(data[8]);


        //they are away
        if(data[3]=="A"){
            home_team.setText(data[6]);
            away_team.setText(data[4]);
            home_score.setText(data[7]);
            away_score.setText(data[5]);

        }else{
            //Stlawrence is home
            home_team.setText(data[4]);
            away_team.setText(data[6]);
            home_score.setText(data[5]);
            away_score.setText(data[7]);

        }


        return v;
    }
    //                       0            1        2    3        4       5          6              7  8
    // data comes in as: "Mens Hockey, 10/7/16, 7:00pm, A, St. Lawrence, 6, Penn State University, 3, W"
    public void populate(String [] data){
        this.data = data;
    }


}
