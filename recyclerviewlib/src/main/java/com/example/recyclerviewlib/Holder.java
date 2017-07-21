package com.example.recyclerviewlib;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/21
 */

public class Holder extends RecyclerView.ViewHolder {

	private Map<Integer, View> mCache;

	public Holder(View itemView) {
		super(itemView);
		mCache = new HashMap<>();
	}

	public View getItemView(){
		return this.itemView;
	}

	@Nullable
	public <T extends View> T findViewById(@IdRes int id){
		if (itemView == null) {
			return null;
		}
		if (mCache.containsKey(id)) {
			return (T) mCache.get(id);
		}
		View result = itemView.findViewById(id);
		mCache.put(id, result);
		if (result == null) {
			return null;
		} else {
			return (T) result;
		}

	}
}
