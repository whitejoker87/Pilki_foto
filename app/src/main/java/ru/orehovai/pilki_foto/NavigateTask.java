package ru.orehovai.pilki_foto;

import android.content.Intent;
import android.os.AsyncTask;

public class NavigateTask extends AsyncTask<Void, Void, String> {

    private final String base64login,navigateUrl;

    public NavigateTask(String base64login, String navigateUrl) {
        this.base64login = base64login;
        this.navigateUrl = navigateUrl;
    }

    @Override
    protected String doInBackground(Void... params) {

        HtmlParser htmlParser = new HtmlParser(navigateUrl);
        if (htmlParser.getParseHtml()){
            return base64login;
        }

        return null;
// переделать вывод base64login
    }

    @Override
    protected void onPostExecute(final String base64login) {

        /*if (base64login != null) {
            Intent intent = new Intent(Main.this, Main.class);
            intent.putExtra("base64login", base64login);
            intent.putExtra("navigateUrl", navigateUrl);
            startActivity(intent);//открываем новую активность
            //startActivity(new Intent(Login.this, Main.class));
            //finish();
        }*/
    }
}
