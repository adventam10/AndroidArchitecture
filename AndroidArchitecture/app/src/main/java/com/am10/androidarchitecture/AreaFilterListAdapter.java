package com.am10.androidarchitecture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;

public class AreaFilterListAdapter extends BaseAdapter {
    public enum Area {
        Hokkaido(0),
        Tohoku(1),
        Kanto(2),
        Chubu(3),
        Kinki(4),
        Chugoku(5),
        Shikoku(6),
        Kyushu(7);

        // フィールドの定義
        public int id;

        // コンストラクタの定義
        private Area(int id) {
            this.id = id;
        }

        private String getName() {
            switch(this) {
                case Hokkaido:
                    return "北海道";
                case Tohoku:
                    return "東北";
                case Kanto:
                    return "関東";
                case Chubu:
                    return "中部";
                case Kinki:
                    return "近畿";
                case Chugoku:
                    return "中国";
                case Shikoku:
                    return "四国";
                case Kyushu:
                    return "九州";
            }
            return "";
        }
    }

    Context context;
    LayoutInflater layoutInflater = null;
    public static final ArrayList<Area> areaList = new ArrayList<>(Arrays.asList(Area.values()));
    ArrayList<Area> areaCheckList;
    private OnAreaFilterListener mListener;

    public AreaFilterListAdapter(Context context, ArrayList<Area> areaCheckList, OnAreaFilterListener listener) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.areaCheckList = areaCheckList;
        this.mListener = listener;
    }

    public void refresh(ArrayList<Area> areaCheckList) {
        this.areaCheckList = areaCheckList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return areaList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.area_filter_list_item, parent,false);
        final Area area = areaList.get(position);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setChecked(areaCheckList.contains(area));
        checkBox.setText(area.getName());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox areaCheckBox = (CheckBox)v;
                mListener.onAreaFilterChecked(area, areaCheckBox.isChecked());
            }
        });
        return convertView;
    }

    public interface OnAreaFilterListener {
        void onAreaFilterChecked(Area area, boolean isCheck);
    }
}
