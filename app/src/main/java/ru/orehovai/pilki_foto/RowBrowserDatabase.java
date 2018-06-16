package ru.orehovai.pilki_foto;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RowBrowser.class}, version = 1, exportSchema = false)
public abstract class RowBrowserDatabase extends RoomDatabase {

    public abstract RowBrowserDao getRowBrowserDao();

}
