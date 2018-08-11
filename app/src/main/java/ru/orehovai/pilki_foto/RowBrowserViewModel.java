package ru.orehovai.pilki_foto;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RowBrowserViewModel extends AndroidViewModel {

    private RowsRepository rowsRepository;
    private LiveData<List<RowBrowser>> mListBrowser;
    private LiveData<RowBrowser> mRow;

     public RowBrowserViewModel(Application application) {
         super(application);
         rowsRepository = new RowsRepository(application);
         mListBrowser = rowsRepository.getListBrowser();
     }

     LiveData<List<RowBrowser>> getListBrowser() {
         return mListBrowser;
    }

    LiveData<RowBrowser> getRow(int id) {
         return rowsRepository.getRow(id);
    }
    public void navigate(String url) { rowsRepository.navigate(); }
}