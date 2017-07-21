package com.example.recyclerviewlib;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter的实现类，
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/21
 */

public abstract class QRecyclAdapter<D> extends RecyclerView.Adapter<Holder> {

	private List<D> mDatas = new ArrayList<>();

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false);
		return new Holder(view);
	}

	protected abstract  @LayoutRes int getItemLayout(int viewType);

	@Override
	public void onBindViewHolder(Holder holder, int position) {
		D itemData = mDatas.get(position);
		onBindViewHolder(holder, position, itemData);
	}

	protected abstract void onBindViewHolder(Holder holder, int position, D data);

	@Override
	public int getItemCount() {
		return mDatas.size();
	}


	public void setDatas(List<D> datas) {
		mDatas.clear();
		addDatas(datas);
	}

	public void addDatas(List<D> datas) {
		if (datas != null && !datas.isEmpty()) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	public void clear(){
		mDatas.clear();
		notifyDataSetChanged();
	}

}
