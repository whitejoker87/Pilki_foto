package ru.orehovai.pilki_foto;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {RowBrowser.class}, version = 1, exportSchema = false)
public abstract class RowBrowserDatabase extends RoomDatabase {

    public abstract RowBrowserDao getRowBrowserDao();

    private static RowBrowserDatabase INSTANCE;

    static RowBrowserDatabase getRowBrowserDatabase (final Context context) {
        if (INSTANCE == null) {
            synchronized (RowBrowserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RowBrowserDatabase.class, "rowBrowserDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
