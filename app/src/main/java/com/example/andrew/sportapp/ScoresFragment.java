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

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private Date d = null;
    private int current_month;
    private int current_day;
    private int current_year;
    private TextView display_date = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");


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
        current_month= d.getMonth();
        current_day = d.getDate();
        current_year = (d.getYear()+1900);
        target_date = Calendar.getInstance();

        String dt =""+current_month+"-"+current_day+"-"+current_year;

        try {
            target_date.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(sdf.format(target_date.getTime()));

        System.out.println(target_date.get(Calendar.DAY_OF_MONTH));


        populateScores();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_clicked();
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
                forward_clicked();

            }
        });


        change_score_date();

        return v;
    }
    //TODO put in the days date depending on what i want to find
    public void populateScores(){
        getData scoreData = new getData();
        //TODO put in the days date
        ArrayList<String> data = scoreData.get_scores(target_date);
        for(int x = 0; x<data.size(); x++){
            number_of_fragments = number_of_fragments+1;
            String [] gameArray = data.get(x).split(",");
            Score_Plate_Fragment plate = new Score_Plate_Fragment();
            plate.populate(gameArray);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.scoresPage, plate, "plate"+x);
            transaction.commit();
        }
    }
    public void forward_clicked(){
        deleteFragments();
        target_date.add(Calendar.DATE, 1);
        populateScores();
        change_score_date();
    }

    public void back_clicked(){
        deleteFragments();
        target_date.add(Calendar.DATE, -1);
        populateScores();
        change_score_date();
    }

    public void change_score_date(){
        display_date.setText(sdf.format(target_date.getTime()));
    }

    public void reset_date(){
        String dt =""+current_month+"-"+current_day+"-"+current_year;
        System.out.println("reset is running and here is the date "+dt);
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
