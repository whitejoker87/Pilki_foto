package ru.orehovai.pilki_foto;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    //private static final String ACTION_FOO = "ru.orehovai.pilki_foto.action.FOO";
    //private static final String ACTION_BAZ = "ru.orehovai.pilki_foto.action.BAZ";

    // TODO: Rename parameters
   // private static final String EXTRA_PARAM1 = "ru.orehovai.pilki_foto.extra.PARAM1";
   // private static final String EXTRA_PARAM2 = "ru.orehovai.pilki_foto.extra.PARAM2";

    HtmlParser htmlParser;

    public static String LOG_TAG = "my_log";

    public DownloadIntentService() {
        super("DownloadIntentService");
    }


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
   /* public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("url");
            Log.d(LOG_TAG, "url in servise " + url);
            htmlParser = new HtmlParser(getBaseContext(), url);
            Intent intent1;
            //if (htmlParser.getParseHtml()) {
                if (url.equals(App.STARTURL)) intent1 = new Intent("openLogin");
                else {
                    intent1 = new Intent("openMain");
                    intent1.putExtra("url", url);
                }

                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent1);

                //stopSelf();
            //}
            /*final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }*/
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    /*private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    /*private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
