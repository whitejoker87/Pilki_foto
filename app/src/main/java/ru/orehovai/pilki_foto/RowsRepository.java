package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RowsRepository {
    private RowBrowserDao mRowBrowserDao;
    private LiveData<List<RowBrowser>> mListBrowser;
    private LiveData<List<RowBrowser>> mListBrowserDesc;

    RowsRepository(Application application) {
        RowBrowserDatabase rowBrowserDatabase = RowBrowserDatabase.getRowBrowserDatabase(application);
        mRowBrowserDao = rowBrowserDatabase.getRowBrowserDao();
        mListBrowser = mRowBrowserDao.getListBrowser();
        mListBrowser = mRowBrowserDao.getListBrowserSortDesc();
    }

    LiveData<List<RowBrowser>> getListBrowser(){
        return mListBrowser;
    }
    LiveData<List<RowBrowser>> getListBrowserSortDesc(){
        return mListBrowserDesc;
    }

    LiveData<RowBrowser> getRow(int id){
        return mRowBrowserDao.getRow(id);
    }
    public void navigate() {}
}

