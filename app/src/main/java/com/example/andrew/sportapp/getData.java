package com.example.andrew.sportapp;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

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

/**
 * Created by Andrew on 11/14/16.
 */



public class getData extends AsyncTask {


    private ArrayList<String> allURls = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();

    private boolean all = false;
    private String stringURL = "";
    private Calendar target_date;
    URL url;
    HttpURLConnection connect;



    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");



    public getData(String stringURL, Calendar targetDate) {
        this.stringURL = stringURL;
        this.target_date = targetDate;


    }


    @Override
    protected Object doInBackground(Object[] params) {
        System.out.println("this has been run");
        try {
            url = new URL(stringURL);

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        try {
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.setDoOutput(true);


            int code = connect.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = connect.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = "test";


                while (line != null) {
                    line = br.readLine();
                    if (line == null) {
                        continue;
                    }

                    if (line.contains("<li>")) {
                        line = line.replace("<li>", "");
                        line = line.replace("</li>", "");
                        String [] line_array = line.split(",");
                        String formatted_date_string = line_array[0];
                        String targetDate_string = sdf.format(target_date.getTime());



                        if(formatted_date_string.equals(targetDate_string)){
                            System.out.println("we added it");
                            ScoresFragment.score_data.add(line);

                        }
                    }
                }
            } else {
                System.out.println("no connection");
            }

        } catch (IOException ex1) {
            ex1.printStackTrace();
        } finally {
            connect.disconnect();


        }

        // 5/11/17, 7:00pm, A, St. Lawrence, 2, Dartmouth University, 1, W

        // 5/12/17, 7:00pm, A, St. Lawrence, 3, Harvard University, 0, W

        return null;
    }
    private String formatString (String stringDate){

        String [] splitData = stringDate.replaceAll("\\s+","").split("/");
        String newdate = "";
        if(splitData[0].length()==1){
            newdate = newdate+"0"+splitData[0]+"/";
        }
        if (splitData[1].length()==1){
            newdate = newdate+"0"+splitData[1]+"/"+splitData[2];
        }
        return newdate;
    }

}
