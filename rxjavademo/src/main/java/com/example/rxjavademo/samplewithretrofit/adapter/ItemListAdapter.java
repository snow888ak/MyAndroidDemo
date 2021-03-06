package com.example.rxjavademo.samplewithretrofit.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recyclerviewlib.Holder;
import com.example.recyclerviewlib.QRecyclAdapter;
import com.example.rxjavademo.R;
import com.example.rxjavademo.samplewithretrofit.model.Item;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class ItemListAdapter extends QRecyclAdapter<Item> {

	@Override
	protected int getItemLayout(int viewType) {
		return R.layout.item_grid;
	}

	@Override
	protected void onBindViewHolder(Holder holder, int position, Item data) {
		ImageView img = holder.findViewById(R.id.item_img);
		Glide.with(holder.getItemView().getContext()).load(data.image_url).into(img);
		TextView title = holder.findViewById(R.id.item_title);
		title.setText(data.description);
	}
}
