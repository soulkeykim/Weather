package com.soulkey.android.weather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soulkey.android.weather.R;
import com.soulkey.android.weather.activity.HistoryListener;
import com.soulkey.android.weather.model.HistoryModel;

import java.text.DateFormat;
import java.util.Date;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class HistoryRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<HistoryModel,
        HistoryRecyclerViewAdapter.ViewHolder> {

    private HistoryListener mHistoryListener;

    public HistoryRecyclerViewAdapter(
            Context context,
            RealmResults<HistoryModel> realmResults,
            boolean automaticUpdate,
            boolean animateIdType,
            String animateExtraColumnName,
            HistoryListener historyListener) {
        super(context, realmResults, automaticUpdate, animateIdType, animateExtraColumnName);
        mHistoryListener = historyListener;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.history_item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final HistoryModel historyModel = realmResults.get(position);
        viewHolder.mContainer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!historyModel.isValid()) {
                            return;
                        }
                        mHistoryListener.selectHistory(historyModel.getName());
                    }
                }
        );

        viewHolder.mDeleteIv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!historyModel.isValid()) {
                            return;
                        }
                        mHistoryListener.deleteHistory(historyModel.getName());
                    }
                }
        );

        viewHolder.mCityNameTv.setText(historyModel.getName());
        viewHolder.mLastTouchedTv.setText(DateFormat.getDateTimeInstance().format(new Date(historyModel.getLastDate())));
    }

    public class ViewHolder extends RealmViewHolder {

        public View mContainer;

        public TextView mCityNameTv;
        public TextView mLastTouchedTv;
        public ImageView mDeleteIv;

        public ViewHolder(View container) {
            super(container);
            mContainer = container;
            mCityNameTv = (TextView) container.findViewById(R.id.city_name_tv);
            mLastTouchedTv = (TextView) container.findViewById(R.id.last_touched_tv);
            mDeleteIv = (ImageView) container.findViewById(R.id.delete_iv);
        }
    }
}

