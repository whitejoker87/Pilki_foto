package ru.orehovai.pilki_photo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RowBrowserDao {

    @Query("Select * from RowBrowser")
    LiveData<List<RowBrowser>> getListBrowser();

    @Query("Select * from RowBrowser order by 'title' desc")
    LiveData<List<RowBrowser>> getListBrowserSortDesc();

    @Query("Select * from RowBrowser where id = :id")
    LiveData<RowBrowser> getRow(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RowBrowser rowBrowser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RowBrowser> listBrowser);

    @Update
    void update(RowBrowser rowBrowser);

    @Delete
    void delete(RowBrowser rowBrowser);

    @Query("DELETE FROM RowBrowser")
    public void nukeTable();
}
