package ru.orehovai.pilki_foto;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;

public class DownloadIntentService extends IntentService {

    HtmlParser htmlParser;

    //public static String LOG_TAG = "my_log";

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("url");
            //Log.d(LOG_TAG, "url in servise " + url);
            htmlParser = new HtmlParser(getBaseContext(), url);
            Intent intent1;
                if (url.equals(App.STARTURL)) intent1 = new Intent("openLogin");
                else {
                    intent1 = new Intent("openMain");
                    intent1.putExtra("url", url);
                }

                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent1);
        }
    }
}
