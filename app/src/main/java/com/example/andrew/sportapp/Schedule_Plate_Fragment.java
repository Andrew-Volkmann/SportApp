package com.example.andrew.sportapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Schedule_Plate_Fragment extends Fragment {

    private String[] data;
    private Date given_date;
    private TextView date = null;
    private TextView otherTeam = null;
    private TextView home_away = null;
    private TextView win_loss = null;
    private TextView score_of_game = null;


    public Schedule_Plate_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule__plate_, container, false);
        date = (TextView) v.findViewById(R.id.time_date);
        otherTeam = (TextView) v.findViewById(R.id.other_team_name);
        home_away = (TextView) v.findViewById(R.id.home_away);
        win_loss = (TextView) v.findViewById(R.id.win_loss);
        score_of_game = (TextView) v.findViewById(R.id.score_of_game);
        for(int x = 0; x<data.length; x++){
            System.out.println("index " + x + "has: " + data[x]);

        }


        String [] array_for_formatting = data[0].split("/");
        Date date_of_game = create_date(array_for_formatting);
        date_of_game.setHours(formatHours(data[1]));

        date.setText(data[0]+" "+data[1]);

        otherTeam.setText(data[5]);
        if(data[2].replaceAll("\\s+","").equals("H")){
            home_away.setText("Home");
        }else{
            home_away.setText("Away");
        }

        //if the game has happened yet
        if(given_date.after(date_of_game)){
            StringBuilder s = new StringBuilder();
            s.append(data[4] + " -" + data[6]);
            score_of_game.setText("");
            score_of_game.append(s.toString());
            if(data[7].replaceAll("\\s+","").equals("W")){
                win_loss.setText("Win");
            }else{
                win_loss.setText("Loss");
            }

            // if the game has not happened yet
        }else{
            win_loss.setText("Not");
            score_of_game.setText("Started");
        }






        return v;
        //   0         1     2        3       4            5             6  7
        // "10/7/16, 7:00pm, A, St. Lawrence, 6, Penn State University, 3, W"

    }
    public void populate(String[] data, Date date){
        this.data = data;
        this.given_date = date;

    }
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public Date create_date(String [] splitdate){
        String s = null;
        if(splitdate[1].length() ==1){
            s = "20"+splitdate[2]+"-"+splitdate[0]+"-0"+splitdate[1];
        }else{
            s = "20"+splitdate[2]+"-"+splitdate[0]+"-"+splitdate[1];
        }
        return parseDate(s);
    }
    public int formatHours(String s){
        String [] badcutString = s.split("");// this array comes in with two blank spaces at the front
        String [] cutString = Arrays.copyOfRange(badcutString, 2, badcutString.length);
        if(cutString[1].equals("2") && cutString[5].equals("p"))
            return 12;
        if (cutString.length==6){
            if(cutString[4].equals("p"))
                return Integer.parseInt(cutString[0]) + 12;
            else
                return Integer.parseInt(cutString[0]);
        }else{
            if(cutString[5].equals("p"))
                return  + Integer.parseInt(cutString[1])+ 22;

            else
                return 10+ Integer.parseInt(cutString[1]);

        }


    }

}
