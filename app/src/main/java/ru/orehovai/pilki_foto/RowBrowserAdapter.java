package ru.orehovai.pilki_foto;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RowBrowserAdapter extends RecyclerView.Adapter<RowBrowserAdapter.RowBrowserViewHolder> {

    public static String LOG_TAG = "my_log";

    class RowBrowserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitleRowBrowser;
        private final TextView tvSizeRowBrowser;
        private final TextView tvTimeStampRowBrowser;
        private final TextView tvHitsRowBrowser;

        private RowBrowserViewHolder(View itemView) {
            super(itemView);
            tvTitleRowBrowser = itemView.findViewById(R.id.tvTitleRowBrowser);
            tvSizeRowBrowser = itemView.findViewById(R.id.tvSizeRowBrowser);
            tvTimeStampRowBrowser = itemView.findViewById(R.id.tvTimeStampRowBrowser);
            tvHitsRowBrowser = itemView.findViewById(R.id.tvHitsRowBrowser);
        }
    }

    private final LayoutInflater mInflater;
    private List<RowBrowser> mListBrowser; // Cached copy of words

    String navigateUrl;

    RowBrowserAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public RowBrowserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row, parent, false);
        return new RowBrowserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RowBrowserViewHolder holder, final int position) {
        if (mListBrowser != null) {
            final RowBrowser current = mListBrowser.get(position);
            holder.tvTitleRowBrowser.setText(current.getTitle());
            holder.tvSizeRowBrowser.setText(current.getSize());
            holder.tvTimeStampRowBrowser.setText(current.getTimeStamp());
            holder.tvHitsRowBrowser.setText(current.getHits());
            /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Log.d("my_log", "title-" + current.getTitle() + ", date-" + current.getTimeStamp());
                    //String addUrl = listBrowser.get(position).getLink();
                    Log.d(LOG_TAG, "position перед getLink " + holder.getAdapterPosition());
                    //String rowLink = rowBrowserDao.getRow(position+1).getLink();//сделать нормально
                    //Login.setURL(Login.getURL() + addUrl);
                    //navigateUrl = App.STARTURL + rowLink;
                     navigateUrl = rowBrowserDao.getRow(position+1).getLink();//сделать нормально
                    Log.d(LOG_TAG,  "full url     " + navigateUrl);
                    if (navigateUrl.contains(".jpg")) {
                        Intent intent = new Intent(Main.this, Image.class);
                        //intent.putExtra("base64login", base64login);
                        intent.putExtra("navigateUrl", navigateUrl);
                        startActivity(intent);//открываем новую активность
                        finish();
                    }else {
                        //new BrowserNavigateTask().execute();
                        startService(new Intent(Main.this,DownloadIntentService.class).putExtra("url", navigateUrl));
                    /*if (base64login != null) {
                        Intent intent = new Intent(Main.this, Main.class);
                        intent.putExtra("base64login", base64login);
                        intent.putExtra("navigateUrl", navigateUrl);
                        startActivity(intent);//открываем новую активность
                    }*/
                    /*rowBrowserAdapter.notifyDataSetChanged();
                }
            });*/
        } else {
            // Covers the case of data not being ready yet.
            holder.tvTitleRowBrowser.setText("No Folder");
        }
    }

    void setListBrowser(List<RowBrowser> listBrowser){
        mListBrowser = listBrowser;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mListBrowser != null)
            return mListBrowser.size();
        else return 0;
    }

    /*Context ctx;
    LayoutInflater lInflater;
    ArrayList<RowBrowser> rows;

    RowBrowserAdapter(Context context, ArrayList<RowBrowser> listBrowser) {
        super(context, R.layout.row, listBrowser);
        ctx = context;
        rows = listBrowser;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return rows.size();
    }

    // элемент по позиции
    @Override
    public RowBrowser getItem(int position) {
        return rows.get(position);
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
    }*/
}