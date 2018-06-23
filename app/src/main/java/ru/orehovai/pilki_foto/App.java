package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    public static final String URL = "http://84.52.96.184:8088";

    public static final String USERNAME = "pilki";
    public static final String PASSWORD = "pilkifoto";

    public static App instance;

    private RowBrowserDatabase rowBrowserDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        rowBrowserDatabase = Room.databaseBuilder(this, RowBrowserDatabase.class, "rowBrowserDatabase").build();
    }

    public static App getInstance() {
        return instance;
    }

    public RowBrowserDatabase getRowBrowserDatabase() {
        return rowBrowserDatabase;
    }

}
