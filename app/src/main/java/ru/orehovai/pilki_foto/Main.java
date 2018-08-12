package ru.orehovai.pilki_foto;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    RecyclerView recyclerViewBrowser;
    ArrayList<RowBrowser> listBrowser;
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

        recyclerViewBrowser = findViewById(R.id.recyclerViewBrowser);

        final RowBrowserAdapter adapter = new RowBrowserAdapter(this);
        recyclerViewBrowser.setAdapter(adapter);
        recyclerViewBrowser.setLayoutManager(new LinearLayoutManager(this));

        rowBrowserViewModel = ViewModelProviders.of(this).get(RowBrowserViewModel.class);//получаем ViewModel

        rowBrowserViewModel.getListBrowser().observe(this, new Observer<List<RowBrowser>>() {//наблюдатель для LiveData
            @Override
            public void onChanged(@Nullable final List<RowBrowser> rowBrowserList) {
                // Update the cached copy of the words in the adapter.
                if (navigateUrl.equals(App.DATEURL)) adapter.setListBrowserDesc(rowBrowserList);
                else adapter.setListBrowserStudy(rowBrowserList);
            }
        });

        ItemClickSupport.addTo(recyclerViewBrowser).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //Log.d(LOG_TAG, "position перед getLink " + position);
                        listBrowser = (ArrayList<RowBrowser>) rowBrowserViewModel.getListBrowser().getValue();
                        assert listBrowser != null;
                        navigateUrl = listBrowser.get(position).getLink();//остаем данные из LiveData
                        //Log.d(LOG_TAG, "full url     " + navigateUrl);
                        if (navigateUrl.contains(".jpg")) {
                            Intent intent = new Intent(Main.this, Image.class);
                            intent.putExtra("navigateUrl", navigateUrl);
                            startActivity(intent);//открываем новую активность
                            finish();
                        } else {
                            startService(new Intent(Main.this, DownloadIntentService.class).putExtra("url", navigateUrl));
                        }
                    }
                });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                navigateUrl = intent.getStringExtra("url");
                Log.d(LOG_TAG,  "url из ответа в мейн " + navigateUrl);
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
            //Log.d(LOG_TAG,  "кнопка назад урл " + navigateUrl);
            startService(new Intent(Main.this, DownloadIntentService.class).putExtra("url", navigateUrl));
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_exit){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                finishAndRemoveTask();
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    finishAffinity();
                } else
                {
                    finish();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
