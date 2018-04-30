package ru.orehovai.pilki_foto;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlParser {

    public static String LOG_TAG = "my_log";

    private String base64login;
    private String url;

    HtmlParser(String base64login, String url)  {
        this.base64login = base64login;
        this.url = url;
    }
    public boolean getParseHtml() {

        Login.getListBrowser().clear();

        int responseStatusCode = 0;
        try {

            Connection connection = Jsoup
                    .connect(url)
                    //.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                    //.ignoreHttpErrors(true)
                    //.timeout(0)
                    .header("Authorization", "Basic " + base64login)
                    .referrer("http://84.52.96.184:8088/");
            Connection.Response response = connection.execute();
            Document doc = null;
            responseStatusCode = response.statusCode();
            if (response.statusCode() == 200) {
                doc = connection.get();
                Element tableForParse = doc.getElementById("files");
                Elements rowsTable = tableForParse.select("tr");
                Log.d(LOG_TAG, rowsTable.size() + "= rowstable. size");
                String _title = "", _size = "", _timeStamp = "", _hints = "", _link = "";

                for (int i = 0; i < rowsTable.size(); i++) {
                    Element row = rowsTable.get(i);
                    Element link = row.getElementsByTag("a").get(0);
                    _link = link.text();
                    Elements cols = row.select("td");
                    for (int j = 0; j < cols.size(); j++) {
                        switch (j) {
                            case 0:
                                _title = (cols.get(j)).text();
                                break;
                            case 1:
                                _size = (cols.get(j)).text();
                                break;
                            case 2:
                                _timeStamp = (cols.get(j)).text();
                                break;
                            case 3:
                                _hints = (cols.get(j)).text();
                                break;
                        }
                    }
                    Log.d(LOG_TAG, _title + "   " + _size + "   " + _timeStamp + "   " + _hints + "  " + _link);
                    RowBrouser rowBrouser = new RowBrouser(/*false,*/ _title, _size, _timeStamp, _hints, _link);
                    Login.getListBrowser().add(rowBrouser);//добавляем элемени в список
                }
            }   else {
                RowBrouser getRSS = new RowBrouser(/*false,*/ "Error", " " + response.statusCode(), base64login, null, null);
                Login.getListBrowser().add(getRSS);//добавляем элемени в список

            }return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG,  "отловили ошибку " + e);

            RowBrouser getRSS = new RowBrouser(/*false, */"Error", " " + e, responseStatusCode + "", base64login, null);
            Login.getListBrowser().add(getRSS);//добавляем элемени в список
            return true;

        }
    }
}