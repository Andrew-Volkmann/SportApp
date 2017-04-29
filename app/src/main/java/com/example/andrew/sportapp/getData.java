package com.example.andrew.sportapp;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andrew on 11/14/16.
 */



public class getData {

    public getData(){

    }

    // TODO put in a date object so i know where in each list to find what i am looking for
    public ArrayList get_scores(Calendar date){
        ArrayList<String> games = new ArrayList<>();
        for (int x = 0; x<=3; x++) {
            String gameString = "Mens Hockey, 10/7/16, 7:00pm, A, St. Lawrence, 6, Penn State University, 3, W";
            games.add(gameString);
        }
        return games;
    }

    public ArrayList get_roster(String team){
        ArrayList<String> rosterData = new ArrayList<>();
        for (int x = 0; x<=20; x++) {
            rosterData.add("5, Kirsten Padalis, Sr., D");
        }
        return rosterData;

    }
    public ArrayList get_schedule(String team){
        ArrayList<String> games = new ArrayList<>();
        for (int x = 0; x<=1; x++) {
            String gameString = "10/7/16, 7:00pm, A, St. Lawrence, 6, Penn State University, 3, W";
            games.add(gameString);
        }
        return games;
    }

}
