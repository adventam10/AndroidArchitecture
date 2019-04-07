package com.am10.androidarchitecture;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PrefectureListRecyclerAdapter extends RecyclerView.Adapter<PrefectureListRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<CityData> mCityDataList;
    private Context mContext;
    private OnRecyclerListener mListener;
    private int mSelectedRow = -1;
    private ArrayList<String> mFavoriteCityIdList;

    public PrefectureListRecyclerAdapter(Context context, OnRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mListener = listener;
    }

    @Override
    public PrefectureListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 表示するレイアウトを設定
        return new ViewHolder(mInflater.inflate(R.layout.prefecture_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        // データ表示
        if (mCityDataList != null && mCityDataList.size() > i && mCityDataList.get(i) != null) {
            CityData data = mCityDataList.get(i);
            viewHolder.textView.setText(data.getName());
            viewHolder.checkBox.setChecked(mFavoriteCityIdList.contains(data.getCityId()));
        } else {
            viewHolder.checkBox.setChecked(false);
        }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerChecked(v, i, viewHolder.checkBox.isChecked());
            }
        });
        if (mSelectedRow == i) {
            viewHolder.rowLayout.setBackgroundColor(Color.parseColor("#e0e0e0"));
        } else {
            viewHolder.rowLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        // クリック処理
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedRow = i;
                mListener.onRecyclerClicked(v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCityDataList != null) {
            return mCityDataList.size();
        } else {
            return 0;
        }
    }

    public void setCityDataList(ArrayList<CityData> cityDataList) {
        mCityDataList = cityDataList;
    }

    public void setFavoriteCityIdList(ArrayList<String> favoriteCityIdList) {
        mFavoriteCityIdList = favoriteCityIdList;
    }

    public void refresh() {
        mSelectedRow = -1;
        notifyDataSetChanged();
    }

    // ViewHolder(固有ならインナークラスでOK)
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;
        LinearLayout rowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_prefecture);
            checkBox = itemView.findViewById(R.id.checkBox_favorite);
            rowLayout = itemView.findViewById(R.id.layout_row);
        }
    }

    public interface OnRecyclerListener {
        void onRecyclerClicked(View v, int position);
        void onRecyclerChecked(View v, int position, boolean isCheck);
    }
}
