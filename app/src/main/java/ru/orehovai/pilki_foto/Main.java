package ru.orehovai.pilki_foto;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    RowBrowserAdapter rowBrowserAdapter;
    ListView listViewBrowser;
    String base64login;
    //ArrayList<RowBrowser> listBrowser;
    //RowBrowserDatabase rowBrowserDatabase;
    RowBrowserDao rowBrowserDao;

    private String navigateUrl;

    public static String LOG_TAG = "my_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        base64login = getIntent().getStringExtra("base64login");
        //listBrowser = Login.getListBrowser();
        //rowBrowserDatabase = App.getInstance().getRowBrowserDatabase();
        rowBrowserDao = App.getInstance().getRowBrowserDatabase().getRowBrowserDao();

        rowBrowserAdapter = new RowBrowserAdapter(this, rowBrowserDao.getListBrowser());
        listViewBrowser = findViewById(R.id.listViewBrowser);
        listViewBrowser.setAdapter(rowBrowserAdapter);

        listViewBrowser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String addUrl = listBrowser.get(position).getLink();
                String rowLink = rowBrowserDao.getRow(position).getLink();
                //Login.setURL(Login.getURL() + addUrl);
                navigateUrl = App.URL + rowLink;
                Log.d(LOG_TAG,  "full url     " + navigateUrl);
                if (navigateUrl.contains(".jpg")) {
                    Intent intent = new Intent(Main.this, Image.class);
                    intent.putExtra("base64login", base64login);
                    intent.putExtra("navigateUrl", navigateUrl);
                    startActivity(intent);//открываем новую активность
                    finish();
                }else {
                    new BrowserNavigateTask().execute();
                    if (base64login != null) {
                        Intent intent = new Intent(Main.this, Main.class);
                        intent.putExtra("base64login", base64login);
                        intent.putExtra("navigateUrl", navigateUrl);
                        startActivity(intent);//открываем новую активность
                    }
                    rowBrowserAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Login.setURL(Login.getURL().substring(0, Login.getURL().lastIndexOf('/')));
        navigateUrl = navigateUrl.substring(0, navigateUrl.lastIndexOf('/'));
        new BrowserNavigateTask().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        rowBrowserAdapter.notifyDataSetChanged();
    }

    public class BrowserNavigateTask extends AsyncTask<Void, Void, String> {

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
    }
}
