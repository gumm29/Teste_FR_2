package com.example.teste_fr_2;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ParseStart extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Log.i("parse ", "inicio");

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // login: user     password: PX4eviERU0JB
        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("81dce0a6e2f72c2d74fe39d5fdba148f04c1923d")
                .clientKey("878324a84ad035655f99a98bf345ea8b9019d03e")
                .server("http://3.17.152.230:80/parse/")
                .build()
        );

//        ParseUser.getCurrentUser().logOut();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
