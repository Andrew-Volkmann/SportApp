package com.example.andrew.sportapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoresFragment extends Fragment {


    ArrayList<String> games = new ArrayList<>();



    private Button back_button = null;
    private Button forward_button = null;
    private FragmentManager manager = null;
    private Calendar target_date= null;
    private int number_of_fragments;
    private Calendar today_calendar;
    private Date d = null;
    private int current_month;
    private int current_day;
    private int current_year;
    private TextView display_date = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    public static ArrayList<String> score_data = new ArrayList<>();
    String dt = "";

    private String stringURL = "";
    URL url;
    HttpURLConnection connect;
    private ArrayList<Thread> allThreads = new ArrayList<>();


    public ScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scores, container, false);
        back_button = (Button) v.findViewById(R.id.previous_day_button);
        forward_button = (Button)v.findViewById(R.id.next_day_button);
        display_date = (TextView)v.findViewById(R.id.date_of_score_page);

        manager=getFragmentManager();
        number_of_fragments=0;


        // get my date
        d = new Date();
        current_month= d.getMonth()+1;
        current_day = d.getDate();
        current_year = (d.getYear()+1900);
        target_date = Calendar.getInstance();
        dt =""+current_month+"-"+current_day+"-"+current_year;



        try {
            populateScores();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    back_clicked();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        display_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_date();
                change_score_date();
            }
        });
        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    forward_clicked();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        SimpleDateFormat sdft = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println(sdft.format(target_date.getTime()));


        change_score_date();

        return v;
    }
    //TODO put in the days date depending on what i want to find
    public void populateScores() throws InterruptedException {
        score_data.clear();
        getData MHS = new getData(MainActivity.MHS, target_date);
        MHS.execute();


        getData MLS = new getData(MainActivity.MLS, target_date);
        MLS.execute();

        getData WHS = new getData(MainActivity.WHS, target_date);
        WHS.execute();

        getData WLS = new getData(MainActivity.WLS, target_date);
        WLS.execute();

        try {
            MHS.get(2300, TimeUnit.MILLISECONDS);
            MLS.get(2300, TimeUnit.MILLISECONDS);
            WHS.get(2300, TimeUnit.MILLISECONDS);
            WLS.get(2300, TimeUnit.MILLISECONDS);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(score_data.size());


        for(int x = 0; x<score_data.size(); x++){
            number_of_fragments = number_of_fragments+1;
            String [] gameArray = score_data.get(x).split(",");
            Score_Plate_Fragment plate = new Score_Plate_Fragment();
            plate.populate(gameArray);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.scoresPage, plate, "plate"+x);
            transaction.commit();
        }
    }
    public void forward_clicked() throws InterruptedException {
        deleteFragments();
        target_date.add(Calendar.DATE, 1);
        populateScores();
        change_score_date();
    }

    public void back_clicked() throws InterruptedException {
        deleteFragments();
        target_date.add(Calendar.DATE, -1);
        populateScores();
        change_score_date();
    }

    public void change_score_date(){
        display_date.setText(sdf.format(target_date.getTime()));
    }

    public void reset_date(){
        try {
            target_date.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteFragments(){
        for(int x = 0; number_of_fragments>x;x++) {
            Score_Plate_Fragment sf = (Score_Plate_Fragment) manager.findFragmentByTag("plate" + x);
            delete_single_fragment(sf);
            }
        number_of_fragments = 0;
    }

    public void delete_single_fragment(Fragment frag){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(frag);
        transaction.commit();
    }

}


