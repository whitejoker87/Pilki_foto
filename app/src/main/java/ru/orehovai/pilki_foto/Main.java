package ru.orehovai.pilki_foto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    //RowBrowserAdapter rowBrowserAdapter;
    //ArrayAdapter<RowBrowser> rowBrowserAdapter;
    RecyclerView recyclerViewBrowser;
    //String base64login;
    ArrayList<RowBrowser> listBrowser;
    //RowBrowserDatabase rowBrowserDatabase;
    RowBrowserDao rowBrowserDao;

    private String navigateUrl;

    public static String LOG_TAG = "my_log";

    private RowBrowserViewModel rowBrowserViewModel;

    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rowBrowserDao = RowBrowserDatabase.getRowBrowserDatabase(this).getRowBrowserDao();

        navigateUrl = getIntent().getStringExtra("url");

        //listBrowser = (ArrayList<RowBrowser>) rowBrowserDao.getListBrowser();
        //rowBrowserDatabase = App.getInstance().getRowBrowserDatabase();


        //rowBrowserAdapter = new RowBrowserAdapter(this, (ArrayList<RowBrowser>)rowBrowserDao.getListBrowser());
        //rowBrowserAdapter = new RowBrowserAdapter(this, listBrowser);
        //rowBrowserAdapter = new RowBrowserAdapter(getBaseContext(), (ArrayList<RowBrowser>)rowBrowserDao.getListBrowser());
        //rowBrowserAdapter.setNotifyOnChange(true);
        recyclerViewBrowser = findViewById(R.id.recyclerViewBrowser);

        final RowBrowserAdapter adapter = new RowBrowserAdapter(this);
        recyclerViewBrowser.setAdapter(adapter);
        recyclerViewBrowser.setLayoutManager(new LinearLayoutManager(this));

        rowBrowserViewModel = ViewModelProviders.of(this).get(RowBrowserViewModel.class);//получаем ViewModel

                rowBrowserViewModel.getListBrowser().observe(this, new Observer<List<RowBrowser>>() {//наблюдатель для LiveData
            @Override
            public void onChanged(@Nullable final List<RowBrowser> rowBrowserList) {
                // Update the cached copy of the words in the adapter.
                adapter.setListBrowser(rowBrowserList);
            }
        });

        ItemClickSupport.addTo(recyclerViewBrowser).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //String addUrl = listBrowser.get(position).getLink();
                        Log.d(LOG_TAG, "position перед getLink " + position);
                        listBrowser = (ArrayList<RowBrowser>) rowBrowserViewModel.getListBrowser().getValue();
                        assert listBrowser != null;
                        navigateUrl = listBrowser.get(position).getLink();//сделать нормально//остаем данные из LiveData
                        Log.d(LOG_TAG, "full url     " + navigateUrl);
                        if (navigateUrl.contains(".jpg")) {
                            Intent intent = new Intent(Main.this, Image.class);
                            intent.putExtra("navigateUrl", navigateUrl);
                            startActivity(intent);//открываем новую активность
                            finish();
                        } else {
                            startService(new Intent(Main.this, DownloadIntentService.class).putExtra("url", navigateUrl));
                    /*if (base64login != null) {
                        Intent intent = new Intent(Main.this, Main.class);
                        intent.putExtra("base64login", base64login);
                        intent.putExtra("navigateUrl", navigateUrl);
                        startActivity(intent);//открываем новую активность
                    }*/
                            //rowBrowserAdapter.notifyDataSetChanged();
                        }
                    }
                });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //String someValue = intent.getStringExtra("someName");
                // ... do something ...

                //intent = new Intent(Main.this, Main.class);
                //intent.putExtra("base64login", base64login);
                //startActivity(intent);//открываем новую активность
                //finish();
                navigateUrl = intent.getStringExtra("url");
                Log.d(LOG_TAG,  "Получаю ответ из сервиса в мейн");
                //Log.d(LOG_TAG,  listBrowser.get(0).getLink());
                //listBrowser = (ArrayList<RowBrowser>) rowBrowserDao.getListBrowser();
                //Log.d(LOG_TAG,  listBrowser.get(0).getLink());

                //rowBrowserAdapter.notifyDataSetChanged();
                //Log.d(LOG_TAG,  listBrowser.get(0).getLink());
                //rowBrowserAdapter = new RowBrowserAdapter(getBaseContext(), (ArrayList<RowBrowser>)rowBrowserDao.getListBrowser());
                //rowBrowserAdapter.setNotifyOnChange(true);
                //listViewBrowser = findViewById(R.id.listViewBrowser);
                //listViewBrowser.setAdapter(rowBrowserAdapter);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("openMain"));


    }

    @Override
    public void onBackPressed() {
        if (navigateUrl == null || navigateUrl.equals(App.STARTURL)) {
            startActivity(new Intent(Main.this, Login.class));
            finish();
        } else {
            //super.onBackPressed();
            navigateUrl = navigateUrl.substring(0, navigateUrl.lastIndexOf('/'));
            Log.d(LOG_TAG,  "кнопка назад урл " + navigateUrl);
            startService(new Intent(Main.this, DownloadIntentService.class).putExtra("url", navigateUrl));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        //rowBrowserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }
}
