package ru.orehovai.pilki_foto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/*
класс для структуры поля списка новостей
 */
@Entity
public class RowBrouser{

    private static final String TAG = "MyLog";

    //private boolean checked;
    @PrimaryKey
    private String title;

    private String size;
    private String timeStamp;
    private String hits;
    private String link;



    public RowBrouser(/*boolean checked,*/ String title, String size, String timeStamp, String hits, String link) {
        //this.checked = checked;
        this.title = title;
        this.size = size;
        this.timeStamp = timeStamp;
        this.hits = hits;
        this.link = link;

    }

 /*   public boolean isChecked() {
        return checked;
    }*/

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

    public String getLink() {
        return link;
    }

/*@Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd - hh:mm:ss");

        String result = getTitle() + "  ( " + simpleDateFormat.format(this.getPubDate()) + " )";
        return result;
    }*/







}
