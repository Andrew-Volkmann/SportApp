package com.example.andrew.sportapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class teamsActivity extends AppCompatActivity {

    TextView title = null;
    Button backButton = null;
    Button rosterButton = null;
    Button scheduleButton = null;
    String teamWeHave = "";
    FragmentManager manager;
    getData teamsData = null;
    private Date date = null;
    private int number_of_fragments;
    private boolean fragments_showing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        manager = getFragmentManager();
        date = new Date();
        number_of_fragments=0;
        fragments_showing = false;
        // get my views
        title = (TextView) findViewById(R.id.team_we_clicked);
        backButton = (Button) findViewById(R.id.back_button);
        rosterButton = (Button) findViewById(R.id.roster_button);
        scheduleButton = (Button) findViewById(R.id.schedule_button);

        //grab intent to fill the title and get the data we need
        Intent myIntent = getIntent();
        teamWeHave = myIntent.getStringExtra("team");
        teamsData = new getData();

        title.setText(teamWeHave);



        //TODO set these to be not clickable
        rosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rosterButton.setClickable(false);
                scheduleButton.setClickable(true);
                populateRoster();
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleButton.setClickable(false);
                rosterButton.setClickable(true);
                populateSchedlue();
            }
        });
    }

    public void populateRoster() {
        if(fragments_showing){
            deleteFragments("Roster");
        }
        ArrayList<String> data = teamsData.get_roster(teamWeHave);
        for (int x = 0; x < data.size(); x++) {
            String[] playerName = data.get(x).split(",");
            Roster_Plate_Fragment plate = new Roster_Plate_Fragment();
            plate.populate(playerName);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.team_page_scrollable, plate, "plate" + x);
            number_of_fragments++;
            transaction.commit();
        }
        fragments_showing= true;
        System.out.println(number_of_fragments);

    }
    public void populateSchedlue(){
        if(fragments_showing){
            deleteFragments("Schedule");
        }
        ArrayList<String> data = teamsData.get_schedule(teamWeHave);
        for (int x = 0; x<data.size(); x++){
            String [] teamGame = data.get(x).split(",");
            Schedule_Plate_Fragment plate = new Schedule_Plate_Fragment();
            plate.populate(teamGame, date);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.team_page_scrollable,plate, "plate"+x);
            number_of_fragments++;
            transaction.commit();
        }
        fragments_showing=true;
        System.out.println(number_of_fragments);
    }

    public void deleteFragments(String s){
        if (s.equals("Schedule")) {
            for(int x = 0; number_of_fragments>x;x++) {
                Roster_Plate_Fragment rf = (Roster_Plate_Fragment)manager.findFragmentByTag("plate" + x);
                delete_single_fragment(rf);
            }
        } else {
            for(int x = 0; number_of_fragments>x;x++) {
                Schedule_Plate_Fragment sf = (Schedule_Plate_Fragment) manager.findFragmentByTag("plate" + x);
                delete_single_fragment(sf);
            }
        }
        number_of_fragments = 0;
        fragments_showing=false;

    }
    public void delete_single_fragment(Fragment frag){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(frag);
        transaction.commit();
    }
}
