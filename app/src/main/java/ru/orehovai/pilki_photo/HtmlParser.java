package ru.orehovai.pilki_photo;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class
HtmlParser {


    public static String LOG_TAG = "my_log";

    private String url;
    private RowBrowser rowBrowser;
    private RowBrowserDatabase rowBrowserDatabase;
    private RowBrowserDao rowBrowserDao;

    private List<RowBrowser> listBrowser;

    HtmlParser(Context context, String url)  {
        this.url = url;

        rowBrowserDatabase = RowBrowserDatabase.getRowBrowserDatabase(context);
        rowBrowserDao = rowBrowserDatabase.getRowBrowserDao();

        rowBrowserDao.nukeTable();

        try {
            listBrowser = new ArrayList<>();

            Connection connection = Jsoup
                    .connect(url)
                    .header("Authorization", "Basic " + App.BASE64LOGIN);
            Connection.Response response = connection.execute();
            Document doc = null;
            if (response.statusCode() == 200) {
                doc = connection.get();
                Element tableForParse = doc.getElementById("files");
                Elements rowsTable = tableForParse.select("tr");
                //Log.d(LOG_TAG, rowsTable.size() + " = rowstable.size");
                String _title = "", _size = "", _timeStamp = "", _hints = "", _link = "";
                int _id;

                for (int i = 0; i < rowsTable.size(); i++) {
                    Element row = rowsTable.get(i);
                    Elements cols = row.select("td");
                    //Log.d(LOG_TAG, "размер cols " + cols.size());
                    if (cols.size() > 0) {
                        for (int j = 0; j < cols.size(); j++) {
                            switch (j) {
                                case 0:
                                    _title = (cols.get(j)).text();
                                    _link = url + "/" + cols.get(j).getElementsByTag("a").get(0).text();
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
                        _id = i;
                        //Log.d(LOG_TAG, _id + "   " + _title + "   " + _size + "   " + _timeStamp + "   " + _hints + "  " + _link);
                        rowBrowser = new RowBrowser(_id, _title, _size, _timeStamp, _hints, _link);
                    }
                    //if (!(_title.equals(""))) rowBrowserDao.insert(rowBrowser);//добавляем элемени в список
                    if (!(_title.equals(""))) listBrowser.add(rowBrowser);//добавляем элемени в список
                }
                rowBrowserDao.insertAll(listBrowser);
            }   else  rowBrowserDao.insert(new RowBrowser(0, "Error", " " + response.statusCode(), null, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d(LOG_TAG,  "отловили ошибку " + e);
            rowBrowserDao.insert(new RowBrowser(0, "Error", " " + e, null, null, null));
        }
    }
}
