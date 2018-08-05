package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Base64;

public class App extends Application {

    public static final String STARTURL = "http://84.52.96.184:8088";

    public static final String USERNAME = "pilki";
    public static final String PASSWORD = "pilkifoto";

    public static final String BASE64LOGIN = new String(Base64.encode((App.USERNAME + ":" + App.PASSWORD).getBytes(), Base64.DEFAULT));

    public static App instance;

    //private RowBrowserDatabase rowBrowserDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //rowBrowserDatabase = Room.databaseBuilder(this, RowBrowserDatabase.class, "rowBrowserDatabase").allowMainThreadQueries().build();//убрать запуск в основном потоке
    }

    public static App getInstance() {
        return instance;
    }

    /*public RowBrowserDatabase getRowBrowserDatabase() {
        return rowBrowserDatabase;
    }*/
//по мануалу https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#12 внедрил LiveData и перенес базу
}
