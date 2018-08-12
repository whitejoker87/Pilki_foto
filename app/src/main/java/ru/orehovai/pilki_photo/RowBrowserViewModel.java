package ru.orehovai.pilki_photo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RowBrowserViewModel extends AndroidViewModel {

    private RowsRepository rowsRepository;
    private LiveData<List<RowBrowser>> mListBrowser;

    public RowBrowserViewModel(Application application) {
        super(application);
        rowsRepository = new RowsRepository(application);
        mListBrowser = rowsRepository.getListBrowser();
    }

    LiveData<List<RowBrowser>> getListBrowser() {
        return mListBrowser;
    }
}