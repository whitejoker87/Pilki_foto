package ru.orehovai.pilki_foto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Main extends AppCompatActivity {

    RowBrowserAdapter rowBrowserAdapter;
    ListView lvBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rowBrowserAdapter = new RowBrowserAdapter(this, Login.getListBrowser());
        lvBrowser = findViewById(R.id.lvBrowser);
        lvBrowser.setAdapter(rowBrowserAdapter);

        lvBrowser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo: обработчик нажатия
            }
        });
    }
}
