package com.am10.androidarchitecture;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PrefectureListFragment extends Fragment {

    public interface OnEventListener {
        public void onAreaFilterClickListener(View v);
        public void onFavoriteCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }

    public PrefectureListRecyclerAdapter adapter;
    public RecyclerView recyclerView;
    public TextView noDataTextView;
    public ImageButton areaFilterButton;
    public CheckBox favoriteCheckBox;
    private OnEventListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_prefecture_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        noDataTextView = view.findViewById(R.id.textView_no_data);
        noDataTextView.setVisibility(View.GONE);
        areaFilterButton = view.findViewById(R.id.button_area_filter);
        areaFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAreaFilterClickListener(v);
                }
            }
        });
        favoriteCheckBox = view.findViewById(R.id.checkBox_favorite);
        favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mListener != null) {
                    mListener.onFavoriteCheckedChanged(buttonView, isChecked);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnEventListener) {
            mListener = (OnEventListener)context;
        }

        if (context instanceof PrefectureListRecyclerAdapter.OnRecyclerListener) {
            adapter = new PrefectureListRecyclerAdapter(context, (PrefectureListRecyclerAdapter.OnRecyclerListener)context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        adapter = null;
    }

    public void setupAdapter(ArrayList<CityData> tableDataList, ArrayList<String> favoriteCityIdList) {
        adapter.setCityDataList(tableDataList);
        adapter.setFavoriteCityIdList(favoriteCityIdList);
    }

    public void notifyItemChanged(int position, ArrayList<String> favoriteCityIdList) {
        adapter.setFavoriteCityIdList(favoriteCityIdList);
        adapter.notifyItemChanged(position);
    }

    public void recyclerViewRefresh() {
        adapter.refresh();
    }

    public void recyclerViewRefresh(ArrayList<CityData> tableDataList) {
        adapter.setCityDataList(tableDataList);
        adapter.refresh();

        if (tableDataList.isEmpty()) {
            noDataTextView.setVisibility(View.VISIBLE);
        } else {
            noDataTextView.setVisibility(View.GONE);
        }
    }
}
