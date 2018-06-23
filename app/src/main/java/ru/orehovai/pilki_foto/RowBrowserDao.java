package ru.orehovai.pilki_foto;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
@Dao
public interface RowBrowserDao {

    @Query("Select * from RowBrowser")
    ArrayList<RowBrowser> getListBrowser();

    @Query("Select * from RowBrowser where id = :id")
    RowBrowser getRow(int id);

    @Insert
    void insert(RowBrowser rowBrowser);

    @Update
    void update(RowBrowser rowBrowser);

    @Delete
    void delete(RowBrowser rowBrowser);

}
