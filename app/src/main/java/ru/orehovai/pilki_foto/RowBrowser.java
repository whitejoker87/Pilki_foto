package ru.orehovai.pilki_foto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RowBrowser {
    @PrimaryKey
    private int id;
    @NonNull
    private String title;
    private String size;
    private String timeStamp;
    private String hits;
    private String link;



    public RowBrowser(int id, @NonNull String title, String size, String timeStamp, String hits, String link) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.timeStamp = timeStamp;
        this.hits = hits;
        this.link = link;

    }

 /*   public boolean isChecked() {
        return checked;
    }*/


    public int getId() {
        return id;
    }
    @NonNull
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


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /*@Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd - hh:mm:ss");

        String result = getTitle() + "  ( " + simpleDateFormat.format(this.getPubDate()) + " )";
        return result;
    }*/







}
