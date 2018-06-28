package ru.orehovai.pilki_foto;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {


    public static String LOG_TAG = "my_log";

    //private String base64login;
    private String url;
    RowBrowser rowBrowser;
    RowBrowserDatabase rowBrowserDatabase;
    RowBrowserDao rowBrowserDao;

    HtmlParser(String url)  {
        //this.base64login = base64login;
        this.url = url;
    }
    public boolean getParseHtml() {

        //Login.getListBrowser().clear();

        rowBrowserDatabase = App.getInstance().getRowBrowserDatabase();
        rowBrowserDao = rowBrowserDatabase.getRowBrowserDao();

        //int responseStatusCode = 0;
        try {

            Connection connection = Jsoup
                    .connect(url)
                    //.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                    //.ignoreHttpErrors(true)
                    //.timeout(0)
                    .header("Authorization", "Basic " + App.BASE64LOGIN);
                    //.referrer("http://84.52.96.184:8088/");
            Connection.Response response = connection.execute();
            Document doc = null;
            //responseStatusCode = response.statusCode();
            if (response.statusCode() == 200) {
                doc = connection.get();
                Element tableForParse = doc.getElementById("files");
                Elements rowsTable = tableForParse.select("tr");
                Log.d(LOG_TAG, rowsTable.size() + "= rowstable. size");
                String _title = "", _size = "", _timeStamp = "", _hints = "", _link = "";
                int _id;

                for (int i = 0; i < rowsTable.size(); i++) {
                    Element row = rowsTable.get(i);
                    //Element link = row.getElementsByTag("a").get(0);
                    //_link = link.text();
                    Elements cols = row.select("td");
                    Log.d(LOG_TAG, "размер колс" + cols.size());
                    for (int j = 0; j < cols.size(); j++) {
                        switch (j) {
                            case 0:
                                _title = (cols.get(j)).text();
                                _link = "/" + cols.get(j).getElementsByTag("a").get(0).text();
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
                    Log.d(LOG_TAG, _id + "   " + _title + "   " + _size + "   " + _timeStamp + "   " + _hints + "  " + _link);
                    rowBrowser = new RowBrowser(_id, _title, _size, _timeStamp, _hints, _link);
                    //if (!(_title.equals(""))) Login.getListBrowser().add(rowBrowser);//добавляем элемени в список
                    if (!(_title.equals(""))) rowBrowserDao.insert(rowBrowser);//добавляем элемени в список
                }
            }   else { return false;
                //RowBrowser getRSS = new RowBrowser(/*false,*/ "Error", " " + response.statusCode(), base64login, null, null);
                //Login.getListBrowser().add(getRSS);//добавляем элемени в список

            }
        return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG,  "отловили ошибку " + e);

            //RowBrowser getRSS = new RowBrowser(/*false, */"Error", " " + e, responseStatusCode + "", base64login, null);
            //Login.getListBrowser().add(getRSS);//добавляем элемени в список
            return true;

        }
    }
}
