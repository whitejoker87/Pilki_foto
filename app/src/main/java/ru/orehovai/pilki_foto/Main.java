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
    ArrayAdapter<RowBrowser> rowBrowserAdapter;
    RecyclerView recyclerViewBrowser;
    //String base64login;
    ArrayList<RowBrowser> listBrowser;
    //RowBrowserDatabase rowBrowserDatabase;
    RowBrowserDao rowBrowserDao;

    private String navigateUrl;

    public static String LOG_TAG = "my_log";

    private RowBrowserViewModel rowBrowserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rowBrowserDao = RowBrowserDatabase.getRowBrowserDatabase(this).getRowBrowserDao();

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

        //recyclerViewBrowser.setAdapter(adapter);

        recyclerViewBrowser.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(LOG_TAG,  "number of row     " + e.getActionIndex());
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            /*@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String addUrl = listBrowser.get(position).getLink();
                Log.d(LOG_TAG, "position перед getLink " + position);
                //String rowLink = rowBrowserDao.getRow(position+1).getLink();//сделать нормально
                //Login.setURL(Login.getURL() + addUrl);
                //navigateUrl = App.STARTURL + rowLink;
                navigateUrl = rowBrowserViewModel.getRow(position+1).getValue().getLink();//сделать нормально//остаем данные из LiveData
                Log.d(LOG_TAG,  "full url     " + navigateUrl);
                if (navigateUrl.contains(".jpg")) {
                    Intent intent = new Intent(Main.this, Image.class);
                    //intent.putExtra("base64login", base64login);
                    intent.putExtra("navigateUrl", navigateUrl);
                    startActivity(intent);//открываем новую активность
                    finish();
                }else {
                    //new BrowserNavigateTask().execute();
                    startService(new Intent(Main.this,DownloadIntentService.class).putExtra("url", navigateUrl));
                    /*if (base64login != null) {
                        Intent intent = new Intent(Main.this, Main.class);
                        intent.putExtra("base64login", base64login);
                        intent.putExtra("navigateUrl", navigateUrl);
                        startActivity(intent);//открываем новую активность
                    }*/
                    /*rowBrowserAdapter.notifyDataSetChanged();
                }
            }*/
        });

        BroadcastReceiver receiver = new BroadcastReceiver() {
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
        super.onBackPressed();
//        Login.setURL(Login.getURL().substring(0, Login.getURL().lastIndexOf('/')));
        navigateUrl = navigateUrl.substring(0, navigateUrl.lastIndexOf('/'));
        //new BrowserNavigateTask().execute();
        startService(new Intent().putExtra("url", navigateUrl));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //rowBrowserAdapter.notifyDataSetChanged();
    }

    /*blic void onRecycleItemClick(View view) {
        Log.d(LOG_TAG, "position перед getLink " + rowBrowserViewModel.getRow());
        //String rowLink = rowBrowserDao.getRow(position+1).getLink();//сделать нормально
        //Login.setURL(Login.getURL() + addUrl);
        //navigateUrl = App.STARTURL + rowLink;
        navigateUrl = rowBrowserDao.getRow(position+1).getLink();//сделать нормально
        Log.d(LOG_TAG,  "full url     " + navigateUrl);
        if (navigateUrl.contains(".jpg")) {
            Intent intent = new Intent(Main.this, Image.class);
            //intent.putExtra("base64login", base64login);
            intent.putExtra("navigateUrl", navigateUrl);
            startActivity(intent);//открываем новую активность
            finish();
        }else {
            //new BrowserNavigateTask().execute();
            startService(new Intent(Main.this,DownloadIntentService.class).putExtra("url", navigateUrl));
                    /*if (base64login != null) {
                        Intent intent = new Intent(Main.this, Main.class);
                        intent.putExtra("base64login", base64login);
                        intent.putExtra("navigateUrl", navigateUrl);
                        startActivity(intent);//открываем новую активность
                    }*/
                    /*rowBrowserAdapter.notifyDataSetChanged();
    }

    /*public class BrowserNavigateTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HtmlParser htmlParser = new HtmlParser(base64login, navigateUrl);
            if (htmlParser.getParseHtml()){
                return base64login;
            }

            return null;

        }

        @Override
        protected void onPostExecute(final String base64login) {

            if (base64login != null) {
                Intent intent = new Intent(Main.this, Main.class);
                intent.putExtra("base64login", base64login);
                intent.putExtra("navigateUrl", navigateUrl);
                startActivity(intent);//открываем новую активность
                //startActivity(new Intent(Login.this, Main.class));
                //finish();
            }
        }
    }*/
}
