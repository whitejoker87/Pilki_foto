package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class RowsRepository {
    private RowBrowserDao mRowBrouserDao;
    private LiveData<List<RowBrowser>> mListBrowser;

    RowsRepository(Application application) {
        RowBrowserDatabase rowBrowserDatabase = RowBrowserDatabase.getRowBrowserDatabase(application);
        mRowBrouserDao = rowBrowserDatabase.getRowBrowserDao();
        mListBrowser = mRowBrouserDao.getListBrowser();
    }

    LiveData<List<RowBrowser>> getListBrowser(){
        return mListBrowser;
    }

    LiveData<RowBrowser> getRow(int id){
        return mRowBrouserDao.getRow(id);
    }
    public void navigate() {}
}

