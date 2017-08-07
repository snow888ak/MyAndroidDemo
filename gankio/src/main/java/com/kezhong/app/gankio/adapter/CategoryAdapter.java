package com.kezhong.app.gankio.adapter;

import android.widget.TextView;

import com.example.recyclerviewlib.Holder;
import com.example.recyclerviewlib.QRecyclAdapter;
import com.kezhong.app.gankio.R;
import com.kezhong.app.gankio.entity.NormalData;

/**
 * Created by Snow on 2017/8/7.
 */

public class CategoryAdapter extends QRecyclAdapter<NormalData> {
    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.fragment_category_list_item;
    }

    @Override
    protected void onBindViewHolder(Holder holder, int position, NormalData data) {
        TextView tvTitle = holder.findViewById(R.id.tv_title);
        TextView tvName = holder.findViewById(R.id.tv_name);
        TextView tvDate = holder.findViewById(R.id.tv_date);
        tvTitle.setText(data.getDesc());
        tvName.setText(data.getWho());
        tvDate.setText(data.getPublishedAt());
    }
}
