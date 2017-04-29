package com.example.andrew.sportapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FragmentManager manager;
    private Button newsButton;
    private Button scoresButton;
    private Button teamsButton;
    private int fragmentCheck; //news is 1, scores is 2, teams is 3
    private Date target_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getFragmentManager();

        // get all the buttons
        newsButton=(Button)findViewById(R.id.newsButton);
        scoresButton=(Button)findViewById(R.id.scoresButton);
        teamsButton=(Button)findViewById(R.id.teamsButton);

        // setup the news fragment which acts as the homepage
        NewsFragment newsFragment = new NewsFragment();
        FragmentTransaction transactionAdd = manager.beginTransaction();
        transactionAdd.add(R.id.sectionViews, newsFragment, "news");
        transactionAdd.commit();

        //set the button click listenters
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsClicked(v);
            }
        });
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoresClicked(v);
            }
        });
        teamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamsClicked(v);
            }
        });




        target_date = new Date();
    }
    public void newsClicked(View v) {
        if (fragmentCheck != 1) {
                ScoresFragment sf = (ScoresFragment) manager.findFragmentByTag("scores");
                System.out.println(sf==null);
            if (sf != null) {
                removeFrag(sf);
            }else {
                TeamsFragment tf = (TeamsFragment) manager.findFragmentByTag("teams");

                removeFrag(tf);
            }
                fragmentCheck = 1;

                NewsFragment newsFragment = new NewsFragment();
                FragmentTransaction transactionAdd = manager.beginTransaction();
                transactionAdd.add(R.id.sectionViews, newsFragment, "news");
                transactionAdd.commit();
            }
    }


    public void scoresClicked(View v){
        if(fragmentCheck!=2){
            NewsFragment nf = (NewsFragment)manager.findFragmentByTag("news");
            if(nf != null){
                removeFrag(nf);
            }else{
                TeamsFragment tf = (TeamsFragment)manager.findFragmentByTag("teams");
                removeFrag(tf);
            }
            fragmentCheck = 2;
            ScoresFragment scoresFragment = new ScoresFragment();
            FragmentTransaction transactionAdd = manager.beginTransaction();
            transactionAdd.add(R.id.sectionViews, scoresFragment, "scores");
            transactionAdd.commit();
        }
    }

    public void teamsClicked(View v){
        if(fragmentCheck !=3){
            NewsFragment nf = (NewsFragment)manager.findFragmentByTag("news");
            if(nf != null){
                removeFrag(nf);
            }else{
                ScoresFragment sf = (ScoresFragment)manager.findFragmentByTag("scores");
                removeFrag(sf);
            }
            fragmentCheck =3;
            TeamsFragment teamsFragment = new TeamsFragment();
            FragmentTransaction transactionAdd = manager.beginTransaction();
            transactionAdd.add(R.id.sectionViews, teamsFragment, "teams");
            transactionAdd.commit();
        }
    }

    public void removeFrag(Fragment f){
        FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(f);
            transaction.commit();
    }
    /*
    //TODO put in the days date depending on what i want to find
    public void populateScores(){
        getData scoreData = new getData();
        //TODO put in the days date
        ArrayList<String> data = scoreData.get_scores(target_date);
        for(int x = 0; x<data.size(); x++){
            String [] gameArray = data.get(x).split(",");
            Score_Plate_Fragment plate = new Score_Plate_Fragment();
            plate.populate(gameArray);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.scoresPage, plate, "plate"+x);
            transaction.commit();

        }
    }
    */


}
