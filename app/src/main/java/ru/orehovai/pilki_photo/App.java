package ru.orehovai.pilki_photo;

import android.app.Application;
import android.util.Base64;

public class App extends Application {

    public static final String STARTURL = "http://84.52.96.184:8088";
    public static final String DATEURL = "http://84.52.96.184:8088/foto";

    public static final String USERNAME = "pilki";
    public static final String PASSWORD = "pilkifoto";

    public static String studyName = "Караваевская";

    public static final String BASE64LOGIN = new String(Base64.encode((App.USERNAME + ":" + App.PASSWORD).getBytes(), Base64.DEFAULT));

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
