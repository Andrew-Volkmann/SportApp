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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class teamsActivity extends AppCompatActivity {

    TextView title = null;
    Button backButton = null;
    Button rosterButton = null;
    Button scheduleButton = null;
    String teamWeHave = "";
    FragmentManager manager;

    private Date date = null;
    private int number_of_fragments;
    private boolean fragments_showing;

    private boolean all = false;
    private String stringURL = "";
    URL url;
    HttpURLConnection connect;
    ArrayList<String> data = new ArrayList<>();





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


        title.setText(teamWeHave);




        //TODO set these to be not clickable
        rosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rosterButton.setClickable(false);
                scheduleButton.setClickable(true);
                try {
                    populateRoster();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleButton.setClickable(false);
                rosterButton.setClickable(true);
                try {
                    populateSchedlue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendback();
            }
        });
    }
    public void sendback(){
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("page", 3);
        startActivity(myIntent);

    }

    public void populateRoster() throws InterruptedException {
        if(fragments_showing){
            deleteFragments("Roster");
        }
        get_roster(teamWeHave);

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

    public void populateSchedlue() throws InterruptedException {
        if(fragments_showing){
            deleteFragments("Schedule");
        }
        get_schedule(teamWeHave);

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
    public void get_roster(String team) throws InterruptedException {
        data.clear();

        if (team.equals(TeamsFragment.M_HOCKEY_ID)){
            stringURL = MainActivity.MHR;
        }
        else if (team.equals(TeamsFragment.M_LAX_ID)){
            stringURL = MainActivity.MLR;
        }
        else if (team.equals(TeamsFragment.W_HOCKEY_ID)){
            stringURL = MainActivity.WHR;
        }
        else if(team.equals(TeamsFragment.W_LAX_ID)){
            stringURL = MainActivity.WLR;
        }
        Thread th = giveThread();
        th.start();
        th.join();


    }
    public void get_schedule(String team) throws InterruptedException {
        data.clear();

        if (team.equals(TeamsFragment.M_HOCKEY_ID)){
            stringURL = MainActivity.MHS;
        }
        else if (team.equals(TeamsFragment.M_LAX_ID)){
            stringURL = MainActivity.MLS;
        }
        else if (team.equals(TeamsFragment.W_HOCKEY_ID)){
            stringURL = MainActivity.WHS;
        }
        else if(team.equals(TeamsFragment.W_LAX_ID)){
            stringURL = MainActivity.WLS;
        }
        Thread th = giveThread();
        th.start();
        th.join();

    }
    public Thread giveThread(){
        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(all){
                    // this is if we are looking for the score


                }else {
                    System.out.println("we are in the thread and it is running the url: "+ stringURL);
                    // if we are looking for a spesific team

                    try {
                        url = new URL(stringURL);
                        System.out.println("url has been created");

                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    try{
                        connect = (HttpURLConnection) url.openConnection();
                        connect.setRequestMethod("GET");
                        connect.setDoOutput(true);
                        System.out.println("we are connected to the url");


                        int code = connect.getResponseCode();
                        if(code == HttpURLConnection.HTTP_OK){
                            InputStream is = connect.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader br = new BufferedReader(isr);
                            String line = "test";

                            while(line != null){
                                line = br.readLine();
                                if(line == null){
                                    continue;
                                }
                                if(line.contains("<li>")){
                                    line = line.replace("<li>", "");
                                    line = line.replace("</li>", "");
                                    data.add(line);
                                }

                            }


                        }else{
                            System.out.println("no connection");
                        }

                    }catch(IOException ex1){
                        ex1.printStackTrace();
                    }finally {
                        connect.disconnect();
                    }

                }
                for (int x = 0; x<data.size(); x++){
                    System.out.println(data.get(x));
                }

            }
        });
        return aThread;

    }
}
