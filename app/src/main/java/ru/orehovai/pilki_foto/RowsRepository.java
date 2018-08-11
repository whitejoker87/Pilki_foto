package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RowsRepository {
    private RowBrowserDao mRowBrowserDao;
    private LiveData<List<RowBrowser>> mListBrowser;

    RowsRepository(Application application) {
        RowBrowserDatabase rowBrowserDatabase = RowBrowserDatabase.getRowBrowserDatabase(application);
        mRowBrowserDao = rowBrowserDatabase.getRowBrowserDao();
        mListBrowser = mRowBrowserDao.getListBrowser();
    }

    LiveData<List<RowBrowser>> getListBrowser(){
        return mListBrowser;
    }

    LiveData<RowBrowser> getRow(int id){
        return mRowBrowserDao.getRow(id);
    }
    public void navigate() {}
}

