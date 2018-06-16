package ru.orehovai.pilki_foto;


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class RowBrowserAdapter extends BaseAdapter {

    //private static final String TAG = "MyLog";

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<RowBrowser> objects;

    RowBrowserAdapter(Context context, ArrayList<RowBrowser> listBrowser) {
        ctx = context;
        objects = listBrowser;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.row, parent, false);
        }

        RowBrowser rowFromPosition = getRowBrowser(position);//получаем элемент списка по позициии

        // заполняем View в пункте списка данными из новостей или закладок: закголовок,цвет
        // и картинка
        ((TextView) view.findViewById(R.id.tvTitleRowBrowser)).setText(rowFromPosition.getTitle());
        ((TextView) view.findViewById(R.id.tvSizeRowBrowser)).setText(rowFromPosition.getSize());
        ((TextView) view.findViewById(R.id.tvTimeStampRowBrowser)).setText(rowFromPosition.getTimeStamp());
        ((TextView) view.findViewById(R.id.tvHitsRowBrowser)).setText(rowFromPosition.getHits());


        return view;
    }

    // новость по позиции
    RowBrowser getRowBrowser(int position) {
        return ((RowBrowser) getItem(position));
    }




}