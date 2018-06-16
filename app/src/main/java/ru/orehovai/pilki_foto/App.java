package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    public static App instance;

    private RowBrowserDatabase rowBrowserDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        rowBrowserDatabase = Room.databaseBuilder(this, RowBrowserDatabase.class, "rowBrowserDatabase").build();
    }

    public static synchronized App getInstance() {
        return instance;
    }

    public RowBrowserDatabase getRowBrowserDatabase() {
        return rowBrowserDatabase;
    }

}
