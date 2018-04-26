package ru.orehovai.pilki_foto;

/*
класс для структуры поля списка новостей
 */

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RowBrouser{

    private static final String TAG = "MyLog";

    private boolean checked;
    private String title;
    private String size;
    private String timeStamp;
    private String hits;


    public RowBrouser(boolean checked, String title, String size, String timeStamp, String hits) {
        this.checked = checked;
        this.title = title;
        this.size = size;
        this.timeStamp = timeStamp;
        this.hits = hits;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getTitle() {
        return title;
    }

    public String getSize() {
        return size;
    }

    public String getTimeStamp() {

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

        return timeStamp;
    }

    public String getHits() {
        return hits;
    }

/*@Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd - hh:mm:ss");

        String result = getTitle() + "  ( " + simpleDateFormat.format(this.getPubDate()) + " )";
        return result;
    }*/







}
