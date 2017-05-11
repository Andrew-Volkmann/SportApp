package com.example.andrew.sportapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    WebView webView = null;

    Button mLax = null;
    final String mLaxURL = "http://saintsathletics.com/archives.aspx?path=mlax";

    Button mHockey = null;
    final String mHockeyURL = "http://saintsathletics.com/archives.aspx?path=mhockey";

    Button wLax = null;
    final String wLaxURL = "http://saintsathletics.com/archives.aspx?path=wlax";

    Button wHockey = null;
    final String wHockeyURL = "http://saintsathletics.com/archives.aspx?path=whockey";

    Button homeButton = null;
    final String homeURL = "http://saintsathletics.com/";

    String currentURL = null;


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news, container, false);
        webView = (WebView) view.findViewById(R.id.webViewId);
        mLax = (Button) view.findViewById(R.id.mLax);
        mHockey = (Button) view.findViewById(R.id.mHockey);
        wLax = (Button) view.findViewById(R.id.wLax);
        wHockey = (Button) view.findViewById(R.id.wHockey);
        homeButton = (Button) view.findViewById(R.id.homePage);

        webView.loadUrl(homeURL);
        currentURL = homeURL;

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        mLax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentURL=mLaxURL;
                newURlLoader(mLaxURL);
            }
        });

        mHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentURL=mHockeyURL;
                newURlLoader(mHockeyURL);
            }
        });
        wLax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentURL=wLaxURL;
                newURlLoader(wLaxURL);
            }
        });
        wHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentURL = wHockeyURL;
                newURlLoader(wHockeyURL);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentURL = homeURL;
                newURlLoader(homeURL);
            }
        });



        return view;
    }
    public void newURlLoader(String URL){
        disableButton();
        final String finalURL = URL;

        webView.loadUrl(finalURL);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


    }
    public void disableButton(){
        setAllClickable();
        if (homeURL.equals(currentURL)){
            homeButton.setClickable(false);
        }
        else if (wHockeyURL.equals(currentURL)){
            wHockey.setClickable(false);
        }
        else if(mLaxURL.equals(currentURL)){
            mLax.setClickable(false);
        }
        else if(wLaxURL.equals(currentURL)){
            wLax.setClickable(false);
        }
        else{
            mHockey.setClickable(false);
        }
    }
    public void setAllClickable(){
        homeButton.setClickable(true);
        wHockey.setClickable(true);
        mHockey.setClickable(true);
        mLax.setClickable(true);
        wLax.setClickable(true);
    }

}
