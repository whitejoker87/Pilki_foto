package ru.orehovai.pilki_photo;

import java.util.Collections;
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
        } else {
            // Covers the case of data not being ready yet.
            holder.tvTitleRowBrowser.setText(R.string.no_folder);
        }
    }

    void setListBrowser(List<RowBrowser> listBrowser){
        mListBrowser = listBrowser;
        notifyDataSetChanged();
    }

    void setListBrowserDesc(List<RowBrowser> listBrowser){
        Collections.reverse(listBrowser);
        setListBrowser(listBrowser);
    }

    void setListBrowserStudy(List<RowBrowser> listBrowser){
        for (RowBrowser rb:listBrowser) {
            if (rb.getTitle().contains(App.studyName)) {
                listBrowser.clear();
                listBrowser.add(rb);
                break;
            }
        }
        setListBrowser(listBrowser);
    }



    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mListBrowser != null)
            return mListBrowser.size();
        else return 0;
    }
}